package com.scoreiq;

import android.util.Log;

public class Player {
	private int movesCount = 0;
	private int score = 0;
	private float scoreMultiplier = 1.0f;
	protected String name = "player";
	private boolean prevMoveSuccess = false;
	protected boolean isMyMove = false;
	
	protected IGameEventListener listener;

	public void setListener(IGameEventListener listener) {
		this.listener = listener;
	}

	public void addScore(int score) {
		this.score += (int) (score * scoreMultiplier);
		Log.d("TEST", String.format("Player %s: addScore(), score=%d", name, score));
	}

	public void increseScoreMultiplier(float scoreMult) {
		scoreMultiplier += scoreMult;
		Log.d("TEST", String.format("Player %s: incraseScoreMult(), score=%d", name, scoreMultiplier));
	}

	public void resetScoreMultiplier() {
		scoreMultiplier = 1.0f;
	}

	public void setPrevMoveSuccess(boolean isIt) {
		prevMoveSuccess = isIt;
	}

	public void addMove() {
		movesCount += 1;
		Log.d("TEST", String.format("Player %s: movesCount=%d", name, movesCount));
	}

	public boolean onTouch(Vector3d camPos, Vector3d ray) {
		return true;
	}

	public void update(float secElapsed) {
	}

	public void getMove() {
		isMyMove = true;
		Log.d("TEST", String.format("Player %s: now My Move<----------------------------"));
	}
}
