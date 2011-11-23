package com.scoreiq;

public class Player {
	private int movesCount = 0;
	private int score = 0;
	private float scoreMultiplier = 1.0f;
	private String name = "player";
	private boolean prevMoveSuccess = false;

	IGameEventListener listener;

	public void setListener(IGameEventListener listener) {
		this.listener = listener;
	}

	public void addScore(int score) {
		this.score += (int) (score * scoreMultiplier);
	}

	public void increseScoreMultiplier(float scoreMult) {
		scoreMultiplier += scoreMult;
	}

	public void resetScoreMultiplier() {
		scoreMultiplier = 1.0f;
	}

	public void setPrevMoveSuccess(boolean isIt) {
		prevMoveSuccess = isIt;
	}

	public void addMove() {
		movesCount += 1;
	}

	public boolean onTouch(Vector3d camPos, Vector3d ray) {
		return true;
	}
}
