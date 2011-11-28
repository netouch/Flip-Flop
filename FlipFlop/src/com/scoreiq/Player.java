package com.scoreiq;

import android.util.Log;

public class Player {
	private static final float ADDSCOREMULTIPLIER = 1.0f; 
	
	private int movesCount = 0;
	private int score = 0;
	private float scoreMultiplier = 1.0f;
	protected String name = "player";

	public void addScore(int score) {
		this.score += (int) (score * scoreMultiplier);
		increseScoreMultiplier(ADDSCOREMULTIPLIER);
		Log.d("TEST", String.format("Player %s: addScore(), score=%d", name, this.score));
	}

	public void increseScoreMultiplier(float scoreMult) {
		scoreMultiplier += scoreMult;
		Log.d("TEST", String.format("Player %s: incraseScoreMult(), score=%f", name, scoreMultiplier));
	}

	public void resetScoreMultiplier() {
		scoreMultiplier = 1.0f;
	}

	public int getScore() {
		return score;
	}
	
	public void addMove() {
		movesCount += 1;
		Log.d("TEST", String.format("Player %s: movesCount=%d", name, movesCount));
	}
	
	public void reset(){
		movesCount = 0;
		score = 0;
		scoreMultiplier = 1.0f;
	}
}
