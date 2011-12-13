package com.scoreiq;

import javax.microedition.khronos.opengles.GL10;

public class MovingPlane extends Plane {
	private static final float MAX_ANGLE = 180;
	private static final int ST_FIRST_HALF = 0;
	private static final int ST_SECOND_HALF = 1;
	private static final int ST_PAUSE = 2;
	private static final int ST_DELAY = 3;
	private static final int ST_STOP = 4;

	private Vector3d addedPos;
	private float curAngle = 0;
	private float angleSpeedPerSec = 0;
	private float amplitude = 1.0f;

	private float delayEndTime = 0.0f;
	private float firstHalfEndTime;
	private float pauseEndTime;
	private float secondHalfEndTime;

	private float timeCounter = 0.0f;
	private int curState = ST_STOP;

	private IGameEventListener listener;
	private String eventString;

	public void setListener(IGameEventListener listener) {
		this.listener = listener;
	}

	public MovingPlane(float width, float height) {
		super(width, height);
		addedPos = new Vector3d();
	}

	@Override
	public void update(float secElapsed) {
		if (curState != ST_STOP) {
			updateState();
			switch (curState) {
			case ST_FIRST_HALF:
			case ST_SECOND_HALF:
				curAngle += angleSpeedPerSec * secElapsed;
				addedPos.x = (float) (amplitude * Math.sin(Math.toRadians((double) curAngle)));
				break;

			case ST_PAUSE:
				break;
			}
			timeCounter += secElapsed;
		}
	}

	private void updateState() {
		if (timeCounter < secondHalfEndTime)
			curState = ST_SECOND_HALF;
		if (timeCounter < pauseEndTime)
			curState = ST_PAUSE;
		if (timeCounter < firstHalfEndTime)
			curState = ST_FIRST_HALF;
		if (timeCounter < delayEndTime)
			curState = ST_DELAY;

		if (timeCounter >= secondHalfEndTime) {
			curState = ST_STOP;
			if (listener != null)
				listener.onGameEvent(new GameEvent(GameEvent.NOTIFICATION_ENDS,
						eventString));
		}
	}

	public void show(float seconds, float delay, String eventString) {
		delayEndTime = delay;
		this.eventString = eventString;

		angleSpeedPerSec = MAX_ANGLE / (2.0f * seconds / 3.0f);
		curAngle = 0.0f;
		timeCounter = 0;

		firstHalfEndTime = delay + seconds / 3.0f;
		pauseEndTime = delay + 2.0f * seconds / 3.0f;
		secondHalfEndTime = delay + seconds;

		if (delayEndTime > 0)
			curState = ST_DELAY;
		else
			curState = ST_FIRST_HALF;
	}

	public void setAmplitude(float amp) {
		amplitude = amp;
	}

	@Override
	protected void multiplyMatricies(GL10 gl) {
		gl.glTranslatef(x + addedPos.x, y + addedPos.y, z + addedPos.z);
		gl.glRotatef(rx, 1, 0, 0);
		gl.glRotatef(ry, 0, 1, 0);
		gl.glRotatef(rz, 0, 0, 1);
	}

}
