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
	
	public GameManager gameManager;
	
	private Camera camera;
	
	public FlipFlopView(Activity act, MatrixGrabber mg){
		super(act);
		Log.d("TEST", String.format("View create start"));
		
		Log.d("TEST", String.format("View now create GameManager"));
		gameManager = new GameManager(act);
		gameManager.createCamera(mg);
		TextureManager.getInstance().setListener(gameManager);
		
		Log.d("TEST", String.format("View create now create renderer"));
		renderer = new OGLRenderer();
		Log.d("TEST", String.format("View.renderer now set camera"));
		renderer.setCamera(gameManager.camera);
		Log.d("TEST", String.format("View.renderer now set Drawing"));
		renderer.setDrawing(gameManager);
		
		setRenderer(renderer);
		Log.d("TEST", String.format("View created"));
	}
	
	public void setCamera(Camera cam){
		camera = cam;
		renderer.setCamera(camera);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		gameManager.onTouch(event.getX(), event.getY());
		return true;
	}
}
