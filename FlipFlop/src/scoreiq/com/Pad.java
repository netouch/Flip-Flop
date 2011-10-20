package scoreiq.com;

public class Pad extends MeshGroup{
	private float rxSpeedPerSecond = 0;
	private float targetRx = 0;
	
	public Pad(){
	}
	
	@Override
	public void update(float secElapsed){
		if(rx<targetRx)rx+=(float)(rxSpeedPerSecond*secElapsed);
	}
	
	public void Rotate(int miliseconds){
		targetRx += 90;
		setRxSpeed(miliseconds);
	}

	private void setRxSpeed(float seconds) {
		if(seconds>0){
			float rxDelta = targetRx - rx;
			rxSpeedPerSecond = rxDelta/seconds;
		}
	}
	
}
