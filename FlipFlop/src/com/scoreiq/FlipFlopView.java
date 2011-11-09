package com.scoreiq;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.app.Activity;

import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.math.*;

public class FlipFlopView extends GLSurfaceView {
	private OGLRenderer renderer;
	
	private GameManager gameManager;
	
	private Camera camera;
	public Vector<Pad> pads = new Vector<Pad>();
	public MeshGroup visibleGroup = new MeshGroup();
	
	
	
	public FlipFlopView(Activity act){
		super(act);
		gameManager = new GameManager(act);
		TextureManager.getInstance().setListener(gameManager);
		renderer = new OGLRenderer();
		setRenderer(renderer);
		
		//TODO:renderer.setDrawing(gameManager);
		renderer.setDrawing(visibleGroup);
		Log.d("TEST", String.format("View created"));
	}
	
	public void setCamera(Camera cam){
		camera = cam;
		renderer.setCamera(camera);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		Vector3d ray;// = new Vector3d();
		ray = camera.getTapRay(event.getX(), event.getY());
		
		int i = getTapedPadNum(ray);
		if(!pads.get(i).isFlipping())pads.get(i).Rotate(180, 1);
		return true;
	}
	
	private int getTapedPadNum(Vector3d ray) {
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
				//if(!pads.get(i).isFlipping())pads.get(i).Rotate(180, 1);
				return i;
				}
		}
		return -1;
	}

	public void addToVisible(Mesh mesh){
		visibleGroup.addMesh(mesh);
	}
	
	public void addPad(Pad mPad){
		pads.add(mPad);
		//Log.d("TEST", String.format("---> View Pad added to pad list"));
		visibleGroup.addMesh(mPad);
		//Log.d("TEST", String.format("---> View Pad added to visible list"));
	}
	
	public void shufflePads(){
		int padsNum = pads.size();
		Vector3d tmpPos;
		
		for(int i=0 ; i < padsNum ; i++){
			int rndPad = (int)(Math.random()*padsNum);
			tmpPos = pads.get(i).getPosition();
			pads.get(i).setPosition(pads.get(rndPad).getPosition());
			pads.get(rndPad).setPosition(tmpPos);
		}
	}
}
