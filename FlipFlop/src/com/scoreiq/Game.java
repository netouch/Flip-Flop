package com.scoreiq;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;

public class Game implements ITouchNMesh, IGameEventListener {
	private static final int NO_PAD = -1;
	private String name = "game";

	private IGameEventListener listener;
	private Activity act;

	private Vector<Pad> pads = new Vector<Pad>();
	private Plane background;

	private Vector<Pad> flipedPads = new Vector<Pad>();
	private int playerOneTouchedPads = 0;
	
	private Vector<Player> players = new Vector<Player>();
	private int playersTouchedPads[] = new int[2];
	private int currentPlayerIndex = 0;

	public Game(Activity act) {
		this.act = act;
		//setPlayers();
	}

	public void setPlayers() {
		Log.d("TEST", String.format("Game: setPlayers().  start"));
		
		Player tmp;
		
		tmp = new HumanPlayer();
		tmp.setListener(this);
		players.add(tmp);
		tmp = null;
		Log.d("TEST", String.format("Game: setPlayers().  Human created"));
		
		tmp = new AiPlayer();
		tmp.setListener(this);
		players.add(new AiPlayer());
		tmp = null;
		
		Log.d("TEST", String.format("Game: setPlayers().  AI created"));
		Log.d("TEST", String.format("Game: setPlayers().  finish"));
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
		for(int i =0;i<players.size();i++){
			players.get(i).onTouch(camPos, ray);
		}
		
		if (eventAction == MotionEvent.ACTION_UP) {
			int i = getTapedPadNum(camPos, ray);
			if (i != NO_PAD)
				if (playerOneTouchedPads < 2) {
					pads.get(i).playerFlip();
					// flipedPads.add(pads.get(i));
					playerOneTouchedPads += 1;
				}
		}
		return true;
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
			} else {
				one.flip();
				two.flip();
			}
			flipedPads.clear();
			playerOneTouchedPads = 0;
		}
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
			if (pads.get(i).isIntersect(x, y) && !pads.get(i).fliped
					&& !pads.get(i).isFlipping()) {
				Log.d("TEST",
						String.format(" --> Index of picked Pad is %d", i));
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
		
		for(int i =0;i<players.size();i++){
			players.get(i).update(secElapsed);
		}
	}

	@Override
	public void onGameEvent(GameEvent event) {
		switch (event.type) {
		case GameEvent.PAD_FLIPPED:
			Log.d("TEST", String.format("Game: receive GameEvent PAD_FLIPPED"));
			flipedPads.add(event.pad);
			checkPadIdentity();
			if (checkEndGame())
				if (listener != null)
					listener.onGameEvent(new GameEvent(GameEvent.GAME_END));
			break;

		case GameEvent.AI_PLAYER_MOVE:
			Log.d("TEST",
					String.format("Game: receive GameEvent AI_PLAYER_MOVE"));
			break;

		case GameEvent.HUMAN_PLAYER_MOVE:
			Log.d("TEST",
					String.format("Game: receive GameEvent HUMAN_PLAYER_MOVE"));
			break;
		}
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
		playerOneTouchedPads = 0;
		
		//currentPlayerIndex = (int)(2*Math.random());
		Log.d("TEST", String.format("Game: reset() players.size()=%d", players.size()));
		currentPlayerIndex = 1;
		if(players.size()==2)players.get(currentPlayerIndex).getMove();
	}
}
