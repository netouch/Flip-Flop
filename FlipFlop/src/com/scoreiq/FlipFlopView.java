package com.scoreiq;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class FlipFlopView extends GLSurfaceView {
	private OGLRenderer renderer;
	
	public GameManager gameManager;
	
	public FlipFlopView(Activity act, MatrixGrabber mg){
		super(act);
		//Log.d("TEST", String.format("View create start"));
		
		//Log.d("TEST", String.format("View now create GameManager"));
		gameManager = new GameManager(act);
		gameManager.createCamera(mg);
		
		TextureManager.getInstance().setListener(gameManager);
		
		//Log.d("TEST", String.format("View create now create renderer"));
		renderer = new OGLRenderer();
		//Log.d("TEST", String.format("View.renderer now set camera"));
		renderer.setCamera(gameManager.camera);
		//Log.d("TEST", String.format("View.renderer now set Drawing"));
		renderer.setDrawing(gameManager);
		
		setRenderer(renderer);
		//Log.d("TEST", String.format("View created"));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_UP)gameManager.onTouch(event);
		return true;
	}
}
