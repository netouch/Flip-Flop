package com.scoreiq;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.app.Activity;

import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.math.*;

import com.scoreiq.util.Vector3d;


public class FlipFlopView extends GLSurfaceView {
	private OGLRenderer renderer;
	private Camera camera;
	public Vector<Pad> pads = new Vector<Pad>();
	public MeshGroup visibleGroup = new MeshGroup();
	
	public FlipFlopView(Activity act){
		super(act);
		renderer = new OGLRenderer();
		setRenderer(renderer);
		
		renderer.setDrawing(visibleGroup);
		Log.d("TEST", String.format("View created"));
	}
	
	public void setCamera(Camera cam){
		camera = cam;
		renderer.setCamera(camera);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		Vector3d ray = new Vector3d();
		ray = camera.getTapRay(event.getX(), event.getY());
		rotateTapedPad(ray);
		return true;
	}
	
	private void rotateTapedPad(Vector3d ray) {
		Log.d("TEST", String.format("Calculate touched pad-------------------"));
		float x;
		float y;
		Vector3d pos = camera.getPosition();
		float multipliyer = pos.z/Math.abs(ray.z);
		Log.d("TEST", String.format("- multipliyer = %f", multipliyer));
		x=pos.x + ray.x*multipliyer;
		y=pos.y + ray.y*multipliyer;
		Log.d("TEST", String.format("- x;y = %f;%f", x,y));
		for(int i=0;i<pads.size();i++){
			if(pads.get(i).isIntersect(x, y)){
				Log.d("TEST", String.format(" --> Index of picked Pad is %d", i));
				if(!pads.get(i).isFlipping())pads.get(i).Rotate(180, 1);
				}
		}
		
	}

	public void addToVisible(Mesh mesh){
		visibleGroup.addMesh(mesh);
	}
	
	public void addPad(Pad mPad){
		pads.add(mPad);
		visibleGroup.addMesh(mPad);
	}
}
