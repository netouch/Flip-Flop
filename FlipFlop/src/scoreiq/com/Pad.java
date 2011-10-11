package scoreiq.com;

public class Pad extends MeshGroup {
	private float rxSpeed = 0;
	private float targetRx = 0;
	
	public Pad(){
	}
	
	@Override
	public void update(long msecElapsed){
		if(rx<targetRx)rx+=(float)(rxSpeed*msecElapsed);
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
