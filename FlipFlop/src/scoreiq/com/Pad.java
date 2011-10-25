package scoreiq.com;

import android.util.Log;

public class Pad extends MeshGroup{
	private float rxSpeedPerSecond = 0;
	private float targetRx = 0;
	
	public Pad(){
	}
	
	@Override
	public void update(float secElapsed){
		super.update(secElapsed);
		
		if(rx<targetRx)rx+=(float)(rxSpeedPerSecond*secElapsed);
		else rxSpeedPerSecond = 0;
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
	}
	
	public Pad getByIndex(int i){
		return (Pad)getByIndex(i);
	}
	
	public boolean isFlipping(){
		if(rxSpeedPerSecond>0)return true;
		else return false;
	}
	
}
