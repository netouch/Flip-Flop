package com.scoreiq;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.util.Log;

public class GameManager implements IGameEventListener, IMesh{
	private Activity act;
	
	public Camera camera;
	private ITouchNMesh currentGameState;
	private Menu menu;
	private Game game;
	private Preloader preloader;
	
	public GameManager(Activity act){
		this.act = act;
		Log.d("TEST", String.format("GameManager created"));
	}
	
	public void onTouch(float x, float y){
		currentGameState.onTouch(camera.getPosition(), camera.getTapRay(x, y));
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		switch(event.type){
		case GameEvent.TEXTURE_MANAGER_READY:
			createMenu();
			break;
		case GameEvent.MENU_START:
			Log.d("TEST", String.format("GameManager resived <START message>"));
			break;
		case GameEvent.MENU_THEME:
			Log.d("TEST", String.format("GameManager resived <THEME message>"));
			break;
		}
	}

	public void createCamera(MatrixGrabber mg) {
		Log.d("TEST", String.format("GameManager createCamera() start"));
		camera = new Camera();
		camera.setMatrixGrabber(mg);
		camera.setPosition(0, 0, 15);
		Log.d("TEST", String.format("GameManager createCamera() finish"));
	}

	private void createMenu() {
		Log.d("TEST", String.format("GameManager createMenu() start"));
		menu = new Menu();
		menu.setListener(this);
		menu.createMenu();
		currentGameState = menu;
		Log.d("TEST", String.format("GameManager createMenu() finish"));
	}


	@Override
	public void draw(GL10 gl) {
		currentGameState.draw(gl);
	}


	@Override
	public void update(float secElapsed) {
		currentGameState.update(secElapsed);
	}
}
