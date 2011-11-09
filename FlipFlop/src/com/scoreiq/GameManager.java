package com.scoreiq;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;

public class GameManager implements IGameEventListener, IMesh{
	private Activity act;
	
	private Camera camera;
	private IMesh currentGameState;
	private Menu menu;
	private Game game;
	private Preloader preloader;
	
	public GameManager(Activity act){
		this.act = act;
	}
	
	
	@Override
	public void onGameEvent(GameEvent event) {
		switch(event.type){
		case GameEvent.TEXTURE_MANAGER_READY:
			createMenu();
			break;
		}
	}


	private void createMenu() {
		menu = new Menu();
		currentGameState = menu;
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
