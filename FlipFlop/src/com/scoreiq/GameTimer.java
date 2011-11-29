package com.scoreiq;

import android.util.Log;

public class GameTimer {
	private int updatesCount = 0;
	IGameEventListener listener;
	private float currentTime = 0;
	private float targetTime = 1.0f; // in seconds
	private boolean active = false;

	public void start() {
		active = true;
	}

	public void stop() {
		active = false;
		currentTime = 0;
	}

	public void setListener(IGameEventListener listener) {
		this.listener = listener;
	}

	public void update(float secElapsed) {
		if (active) {
			currentTime += secElapsed;
			updatesCount++;
		}

		if (currentTime >= targetTime) {
			dispatchEvent();
			currentTime = 0;
			updatesCount = 0;
		}
	}

	private void dispatchEvent() {
		if (listener != null)listener.onGameEvent(new GameEvent(GameEvent.TIMER_EVENT));
	/*	
		Log.d("TEST", String.format(
				"GameTimer -------------TICK---------------\n FPS = %d",
				(int) (updatesCount / targetTime)));
				*/
	}
}
