package com.scoreiq;

import android.util.Log;

public class AiPlayer extends Player {
	private static final float TIME_TO_MOVE = 2;
	
	private float targetMoveTime = TIME_TO_MOVE;
	private float curMoveTime = 0;

	public AiPlayer(){
		name = "AiPlayer";
	}

	@Override
	public void update(float secElapsed) {
		if(isMyMove){
			curMoveTime += secElapsed;
			if(curMoveTime >=targetMoveTime){
				isMyMove = false;
				makeMove();
			}
		}
	}

	private void makeMove() {
		int i = (int)(12*Math.random());
		if(listener!=null)
			listener.onGameEvent(new GameEvent(GameEvent.AI_PLAYER_MOVE, i));
		Log.d("TEST", String.format("Player %s: makeMove(), choosen pad #=%d", i));
	}
	
	@Override
	public void getMove(){
		Log.d("TEST", String.format("Player %s: getMove()", name));
		isMyMove = true;
		targetMoveTime = TIME_TO_MOVE;
		curMoveTime = 0;
	}
}
