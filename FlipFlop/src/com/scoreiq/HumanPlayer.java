package com.scoreiq;

public class HumanPlayer extends Player {
	public HumanPlayer(){
		name = "HumanPlayer";
	}
	
	@Override
	public boolean onTouch(Vector3d camPos, Vector3d ray){
		if(isMyMove){
			listener.onGameEvent(new GameEvent(GameEvent.HUMAN_PLAYER_MOVE, camPos, ray));
			isMyMove = false;
		}
		return true;
	}
}
 