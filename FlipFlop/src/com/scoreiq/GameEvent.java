package com.scoreiq;

public class GameEvent {
	public int type = 0;
	
	GameEvent(int type){
		this.type = type;
	}
	
	static final int TEXTURE_MANAGER_READY = 1;
	static final int MENU_START = 2;
	static final int MENU_THEME = 3;
}
