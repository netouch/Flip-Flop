package com.scoreiq;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;

public class GameManager implements IGameEventListener, IMesh{
	private Activity act;
	
	public Camera camera;
	private ITouchNMesh currentGameState;
	private Menu menu;
	private Game game;
	//private Preloader preloader;
	private String theme = "default/";
	
	public GameManager(Activity act){
		this.act = act;
		Log.d("TEST", String.format("GameManager created"));
	}
	
	public void onTouch(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_UP){	
			float x = event.getX();
			float y = event.getY();
			if(currentGameState!=null)
				currentGameState.onTouch(camera.getPosition(), camera.getTapRay(x, y), MotionEvent.ACTION_UP);
		}
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		switch(event.type){
		case GameEvent.TEXTURE_MANAGER_READY:
			TextureManager.getInstance().loadAllTexturesIn("rio/");
			TextureManager.getInstance().loadAllTexturesIn("default/");
			createMenu();
			createGame();
			currentGameState = menu;
			break;
		case GameEvent.MENU_START:
			Log.d("TEST", String.format("GameManager recived <START message>"));
			game.reset(theme);
			currentGameState = null;
			currentGameState = game;
			//createGame();
			break;
		case GameEvent.MENU_THEME:
			Log.d("TEST", String.format("GameManager recived <THEME message>"));
			break;
		case GameEvent.GAME_END:
			currentGameState = null;
			currentGameState = menu;
			break;
		case GameEvent.THEME_SELECT:
			theme = event.theme;
			break;
		}
	}

	private void createGame() {
		if(game==null){
			game = new Game(act);
			game.setListener(this);
			//game.loadLevel("rio/");
			game.loadLevel("default/");
			game.setPlayers();
		}
		//currentGameState = game;
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
		//currentGameState = menu;
		Log.d("TEST", String.format("GameManager createMenu() finish"));
	}

	public boolean isMenuState(){
		if(currentGameState.getName().equalsIgnoreCase("menu"))return true;
		else return false;
	}

	@Override
	public void draw(GL10 gl) {
		if(currentGameState!=null)currentGameState.draw(gl);
	}


	@Override
	public void update(float secElapsed) {
		currentGameState.update(secElapsed);
	}
}
