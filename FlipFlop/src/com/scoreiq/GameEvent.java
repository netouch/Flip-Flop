package com.scoreiq;

public class GameEvent {
	public int type = 0;
	public Pad pad;
	
	public GameEvent(int type){
		this.type = type;
	}
	
	public GameEvent(int type , Pad pad){
		this(type);
		this.pad = pad;
	}
	
	static final int TEXTURE_MANAGER_READY = 1;
	static final int MENU_START = 2;
	static final int MENU_THEME = 3;
	static final int PAD_FLIPPED = 4;
}

