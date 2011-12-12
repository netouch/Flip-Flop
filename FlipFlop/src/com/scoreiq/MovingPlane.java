package com.scoreiq;

import javax.microedition.khronos.opengles.GL10;

public class MovingPlane extends Plane {
	private static final float MAX_ANGLE = 180;
	private Vector3d addedPos;
	private float curAngle=0;
	private float angleSpeedPerSec = 0;
	private float amplitude = 1.0f;
	
	private float startDelay = 0.0f;
	private float startDelayCounter = 0.0f;
	
	private IGameEventListener listener;
	private String eventString;
	
	public void setListener(IGameEventListener listener) {
		this.listener = listener;
	}
	
	public MovingPlane(float width, float height){
		super(width, height);
		addedPos = new Vector3d();
	}
	
	@Override
	public void update(float secElapsed){
		if(startDelayCounter>startDelay){
			if(angleSpeedPerSec!=0){
				curAngle+=angleSpeedPerSec*secElapsed;
				addedPos.x = (float)(amplitude * Math.sin(Math.toRadians((double)curAngle)));
				
				if(curAngle>MAX_ANGLE){
					addedPos.set(0, 0, 0, 0);
					curAngle = 0;
					angleSpeedPerSec = 0;
					if(listener!=null)listener.onGameEvent(new GameEvent(GameEvent.NOTIFICATION_ENDS, eventString));
				}
			}
		}
		else startDelayCounter+=secElapsed;
			
	}
	
	public void show(float seconds, float delay, String eventString){
		angleSpeedPerSec = MAX_ANGLE/seconds;
		startDelay = delay;
		this.eventString = eventString;
	}
	
	public void setAmplitude(float amp){
		amplitude = amp;
	}
	
	@Override
	protected void multiplyMatricies(GL10 gl){
		gl.glTranslatef(x+addedPos.x, y + addedPos.y, z+addedPos.z);
	    gl.glRotatef(rx, 1, 0, 0);
	    gl.glRotatef(ry, 0, 1, 0);
	    gl.glRotatef(rz, 0, 0, 1);
	}
	
	
}
