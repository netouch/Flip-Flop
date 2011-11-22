package com.scoreiq;

import android.util.Log;

public class Pad extends MeshGroup{
	private float rxSpeedPerSecond = 0;
	private float targetRx = 0;
	private float maxX , minX;
	private float maxY, minY;
	public boolean isActive = true;
	private boolean isNotIdle = false;
	private boolean sendMessage = false;
	
	public int faceImageId = 0;
	public int id;
	
	public boolean fliped = false;
	
	private IGameEventListener listener = null;
	
	public Pad(){
		maxX = maxY = 1.5f;
		minX = minY = -1.5f;
		rxSpeedPerSecond = 0.0f;
	}
	
	public Pad(IGameEventListener list){
		this();
		listener = list;
	}
	
	@Override
	public void update(float secElapsed){
		super.update(secElapsed);
		if(isNotIdle){
			if(rx<targetRx)rx+=(float)(rxSpeedPerSecond*secElapsed);
			else{
				rxSpeedPerSecond = 0;
				isNotIdle = false;
				if(listener != null && sendMessage){
					listener.onGameEvent(new GameEvent(GameEvent.PAD_FLIPPED, this));
					sendMessage = false;
				}
			}
		}
	}
	
	public boolean isIntersect(float ix, float iy){
		//Log.d("TEST", String.format("---Pad.isIntersect"));
		//Log.d("TEST", String.format("--- cur pad pos is (%f;%f;%f) and picked pos is(%f;%f)", x,y,z, ix,iy));
		if( (x+minX)<ix && ix<(x+maxX) && (y+minY)<iy && iy<(y+maxY))return true;
		else return false;
	}
	
	public void Rotate(int angle, float seconds){
		targetRx += angle;
		setRxSpeed(seconds);
	}

	private void setRxSpeed(float seconds) {
		if(seconds>0){
			float rxDelta = targetRx - rx;
			rxSpeedPerSecond = rxDelta/seconds;
		}
		isNotIdle = true;
	}
	
	public boolean isFlipping(){
		Log.d("TEST", String.format("rxSpeedPerSecond = %f", rxSpeedPerSecond));
		if(rxSpeedPerSecond>0)return true;
		else return false;
	}
	
	public void flip(){
		Rotate(180, 1);
		fliped = ! fliped;
	}
	
	public void playerFlip(){
		flip();
		sendMessage = true;
	}
	
	public void reset(){
		rx = 270.0f;
		fliped = false;
		isActive = true;
		isNotIdle = false;
		sendMessage = false;
		targetRx = 270.0f;
		rxSpeedPerSecond = 0.0f;
	}
}
