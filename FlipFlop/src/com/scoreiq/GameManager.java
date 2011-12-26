package com.scoreiq;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

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
			loadTextures();
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
		case GameEvent.MENU_OPTIONS:
			showOptions();
			break;
			
		case GameEvent.SEND_FEEDBACK:	
			sendFeedback();
			break;
		}
	}

	private void showOptions() {
		Intent i_opt = new Intent(act.getApplicationContext(), OptionsScreen.class);
		i_opt.putExtra("sound", SoundManager.getInstance().getSoundOn());
		i_opt.putExtra("vibration", VibratorManager.getInstance().getEnable());
		act.startActivityForResult(i_opt, Activity.RESULT_FIRST_USER);
		//act.startActivity(i_opt);
	}

	private void sendFeedback(){
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"scoreiq@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "FlipFlop feedback");
		i.putExtra(Intent.EXTRA_TEXT   , "Enter text here...");
		try {
		    act.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(act, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void loadTextures() {
		TextureManager tm = TextureManager.getInstance();
		tm.loadAllTexturesIn("rio/");
		tm.loadAllTexturesIn("default/");
		tm.loadAllTexturesIn("newyear/");
		
		tm.loadTexture("numbers.png");
		tm.loadTexture("numbers2.png");
		tm.loadTexture("robot_eng.png");
		tm.loadTexture("human_eng.png");
		
		tm.loadTexture("menu_bg2.png");
		tm.loadTexture("menu.png");
		tm.loadTexture("menu_press.png");
		
		tm.loadTexture("winners.png");
	}

	private void createGame() {
		if(game==null){
			game = new Game(act, camera);
			game.setListener(this);
			//game.loadLevel("rio/");
			game.loadLevel("default/");
		}
		//currentGameState = game;
	}

	public void createCamera(MatrixGrabber mg) {
		//Log.d("TEST", String.format("GameManager createCamera() start"));
		camera = new Camera();
		camera.setMatrixGrabber(mg);
		camera.setPosition(0, 0, 15);
		//Log.d("TEST", String.format("GameManager createCamera() finish"));
	}

	private void createMenu() {
		//Log.d("TEST", String.format("GameManager createMenu() start"));
		menu = new Menu();
		menu.setListener(this);
		menu.createMenu();
		//currentGameState = menu;
		//Log.d("TEST", String.format("GameManager createMenu() finish"));
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
