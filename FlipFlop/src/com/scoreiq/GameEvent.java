package com.scoreiq;

public class GameEvent {
	static final int TEXTURE_MANAGER_READY = 1;
	static final int MENU_START = 2;
	static final int MENU_THEME = 3;
	static final int PAD_FLIPPED = 4;
	static final int GAME_END = 5;
	static final int THEME_SELECT = 6;
	static final int HUMAN_PLAYER_MOVE = 7;
	static final int AI_PLAYER_MOVE = 8;
	static final int TIMER_EVENT = 9;
	static final int NOTIFICATION_ENDS = 10;

	
	public int type = 0;
	public Pad pad;
	public String theme;

	public int tapedPad = -1;

	public Vector3d camPos;
	public Vector3d ray;

	public GameEvent(int type) {
		this.type = type;
	}

	public GameEvent(int type, Pad pad) {
		this(type);
		this.pad = pad;
	}

	public GameEvent(int type, String theme) {
		this(type);
		this.theme = theme;
	}

	public GameEvent(int type, int tapedPad) {
		this(type);
		this.tapedPad = tapedPad;
	}

	public GameEvent(int type, Vector3d pos, Vector3d ray) {
		this(type);
		camPos = pos;
		this.ray = ray;
	}

}
