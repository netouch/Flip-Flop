package com.scoreiq;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.test.IsolatedContext;
import android.util.Log;
import android.view.MotionEvent;

public class Game implements ITouchNMesh, IGameEventListener {
	private static final int NO_PAD = -1;
	private static final int SUCCESS_SCORE = 5;
	
	private String name = "game";

	private IGameEventListener listener;
	private Activity act;

	private Vector<Pad> pads = new Vector<Pad>();
	private Plane background;

	private Vector<Pad> flipedPads = new Vector<Pad>();
	private int playerOneTouchedPads = 0;

	private GameTimer timer;

	private Player humanPlayer;
	private boolean humanPlayerMove = false;
	private int humanTouchedPads = 0;
	
	private AiPlayer aiPlayer;
	private boolean aiPlayerMove = false;
	private int aiTouchedPads;

	public Game(Activity act) {
		this.act = act;

		timer = new GameTimer();
		timer.setListener(this);
	}

	public void setPlayers() {
		humanPlayer = new Player();
		aiPlayer = new AiPlayer();

		if ((int) Math.random() > 0)
			humanPlayerMove = true;
		else
			aiPlayerMove = true;
		//TODO: temporary for test. del later
		aiPlayerMove = true;
		timer.start();
	}

	public void swapPlayersMove() {
		humanPlayerMove = !humanPlayerMove;
		aiPlayerMove = !aiPlayerMove;
	}

	public String getName() {
		return name;
	}

	public void setListener(IGameEventListener lnr) {
		listener = lnr;
	}

	public void loadLevel(String theme) {
		if (theme == "")
			theme = "default/";

		MeshBuilder builder_top = new MeshBuilder(act);
		MeshBuilder builder_bottom = new MeshBuilder(act);

		builder_top.loadObjToClone("pad_top3d.obj");
		builder_bottom.loadObjToClone("pad_bottom3d.obj");

		Mesh tmpMesh;
		Pad tmpPad;

		int fileNum = 1;
		for (int y = 0; y < 4; y++)
			for (int x = 0; x < 3; x++) {
				tmpPad = new Pad(this);

				tmpMesh = builder_top.cloneMesh();
				tmpPad.addMesh(tmpMesh);

				tmpMesh = builder_bottom.cloneMesh();
				tmpPad.addMesh(tmpMesh);
				tmpPad.id = y * 4 + x;

				tmpPad.reset();
				tmpPad.x += -2.5 + x * 2.5;
				tmpPad.y += 4.0 - y * 2.7;

				pads.add(tmpPad);

				fileNum++;
				if (fileNum > 6)
					fileNum = 1;
			}

		background = new Plane(16.0f, 16.0f);
		background.z += -4.0f;

		reset(theme);
	}

	private void shufflePads() {
		int padsNum = pads.size();
		Vector3d tmpPos;

		for (int i = 0; i < padsNum; i++) {
			int rndPad = (int) (Math.random() * padsNum);
			tmpPos = pads.get(i).getPosition();
			pads.get(i).setPosition(pads.get(rndPad).getPosition());
			pads.get(rndPad).setPosition(tmpPos);
		}
	}

	public boolean onTouch(Vector3d camPos, Vector3d ray, int eventAction) {
		if (eventAction == MotionEvent.ACTION_UP && humanPlayerMove) {
			int i = getTapedPadNum(camPos, ray);
			if (i != NO_PAD)
				if (playerOneTouchedPads < 2 && humanTouchedPads < 2) {
					aiPlayer.rememberPad(i, pads.get(i).faceImageId);
					pads.get(i).playerFlip();
					// flipedPads.add(pads.get(i));
					playerOneTouchedPads ++;
					humanTouchedPads ++;
				}
		}
		return true;
	}

	private int getTapedPadNum(Vector3d camPos, Vector3d ray) {
		Log.d("TEST", String.format("Calculate touched pad-------------------"));
		float x;
		float y;
		// Vector3d pos = camera.getPosition();
		float multipliyer = camPos.z / Math.abs(ray.z);
		Log.d("TEST", String.format("- multipliyer = %f", multipliyer));
		x = camPos.x + ray.x * multipliyer;
		y = camPos.y + ray.y * multipliyer;
		Log.d("TEST", String.format("- x;y = %f;%f", x, y));
		for (int i = 0; i < pads.size(); i++) {
			if (pads.get(i).isIntersect(x, y) && 
			!pads.get(i).fliped &&
			!pads.get(i).isFlipping() &&
			pads.get(i).isActive) {
				Log.d("TEST", String.format(" --> Index of picked Pad is %d", i));
				return i;
			}
		}
		return NO_PAD;
	}

	@Override
	public void draw(GL10 gl) {
		if (background != null)
			background.draw(gl);

		Pad tmpPad;
		for (int i = 0; i < pads.size(); i++) {
			tmpPad = pads.get(i);
			if (tmpPad.isActive)
				tmpPad.draw(gl);
		}
	}

	@Override
	public void update(float secElapsed) {
		Pad tmpPad;
		for (int i = 0; i < pads.size(); i++) {
			tmpPad = pads.get(i);
			if (tmpPad.isActive)
				tmpPad.update(secElapsed);
		}

		timer.update(secElapsed);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		switch (event.type) {
		case GameEvent.PAD_FLIPPED:
			Log.d("TEST", String.format("Game: receive GameEvent PAD_FLIPPED"));
			flipedPads.add(event.pad);
			checkPadIdentity();
			if (checkEndGame()){
				Log.d("TEST", String.format("Game: END GAME!!!!"));
				Log.d("TEST", String.format("Game: player Score is %d , ai Csore is %d", humanPlayer.getScore(), aiPlayer.getScore()));

				if (listener != null)
					listener.onGameEvent(new GameEvent(GameEvent.GAME_END));
			}
			break;

		case GameEvent.TIMER_EVENT:
			Log.d("TEST", String.format("Game: receive GameEvent TIMER_EVENT"));
			if(aiPlayerMove){
				int i = aiPlayer.getMove();
				if (i != NO_PAD)
					if (aiTouchedPads < 2 &&
							!pads.get(i).fliped &&
							!pads.get(i).isFlipping() &&
							pads.get(i).isActive) {
						aiPlayer.rememberPad(i, pads.get(i).faceImageId);
						pads.get(i).playerFlip();
						aiTouchedPads++;
					}
			}
			break;
		}
	}

	private void checkPadIdentity() {
		Log.d("TEST", String.format("Game: checkPadIdentity()"));
		if (flipedPads.size() == 2) {
			Log.d("TEST", String.format("Game: checkPadIdentity() size=%d",
					flipedPads.size()));
			Pad one, two;
			one = flipedPads.get(0);
			two = flipedPads.get(1);
			
			
			if (one.faceImageId == two.faceImageId) {
				one.isActive = false;
				two.isActive = false;
				
				for(int i=0;i<pads.size();i++)
					if(pads.get(i)==one || pads.get(i)==two)aiPlayer.setInactive(i);
				
				SoundManager.getInstance().playSound(SoundManager.SUCCESS, 1.0f);
				getActivePlayer().addScore(SUCCESS_SCORE);
				
			} else {
				one.flip();
				two.flip();
				SoundManager.getInstance().playSound(SoundManager.FAIL, 1.0f);
				getActivePlayer().resetScoreMultiplier();
			}
			
			getActivePlayer().addMove();

			flipedPads.clear();
			playerOneTouchedPads = 0;
			humanTouchedPads = 0;
			aiTouchedPads=0;
			
			swapPlayersMove();
		}
	}

	private Player getActivePlayer(){
		if(humanPlayerMove)return humanPlayer;
		else return (Player)aiPlayer;
	}
	
	private boolean checkEndGame() {
		boolean isActivePads = false;
		for (int i = 0; i < pads.size(); i++)
			if (pads.get(i).isActive)
				isActivePads = true;
		return !isActivePads;
	}

	public void reset(String theme) {
		TextureManager tm = TextureManager.getInstance();
		int imgNum = 1;

		for (int i = 0; i < pads.size(); i++) {
			pads.get(i).isActive = true;

			pads.get(i)
					.getByIndex(0)
					.setTextureId(
							tm.loadTexture(theme + "fr" + imgNum + ".png"));
			pads.get(i).faceImageId = imgNum;
			imgNum++;
			if (imgNum > 6)
				imgNum = 1;

			pads.get(i).getByIndex(1)
					.setTextureId(tm.loadTexture(theme + "back.png"));

			pads.get(i).reset();
		}

		background.setTextureId(tm.loadTexture(theme + "background.png"));
		shufflePads();

		flipedPads.clear();
		if(aiPlayer != null)aiPlayer.reset();
		playerOneTouchedPads = 0;
	}
}
