package scoreiq.com;

public class Pad extends MeshGroup{
	private float rxSpeed = 0;
	private float targetRx = 0;
	
	Mesh top;
	Mesh bottom;
	
	public Pad(Mesh m_top, Mesh m_bottom){
		this.addMesh(m_top);
		this.addMesh(m_bottom);
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
