package com.scoreiq;

public class HumanPlayer extends Player {
	@Override
	public boolean onTouch(Vector3d camPos, Vector3d ray){
		listener.onGameEvent(new GameEvent(GameEvent.HUMAN_PLAYER_MOVE, camPos, ray));
		return true;
	}
}
 