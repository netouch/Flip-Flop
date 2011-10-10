package scoreiq.com;

import java.util.Date;

public class Pad extends MeshGroup {
	private float rxSpeed = 0;
	private float targetRx = 0;
	private static Date date;
	private long prevTime;
	private long curTime;
	
	public Pad(){
		date = new Date();
	}
	
	@Override
	public void update(){
		curTime = date.getTime();
		long msecElapsed = curTime - prevTime;
		if(rx<targetRx)rx+=(float)(rxSpeed*msecElapsed);
		prevTime = curTime;
	}
	
	public void Rotate(int miliseconds){
		targetRx += 90;
		setRxSpeed(miliseconds);
	}

	private void setRxSpeed(int miliseconds) {
		if(miliseconds>0){
			float rxDelta = targetRx - rx;
			rxSpeed = rxDelta/miliseconds;
		}
	}
}
