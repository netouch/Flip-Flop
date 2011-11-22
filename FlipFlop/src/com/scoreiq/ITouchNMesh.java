package com.scoreiq;

public interface ITouchNMesh extends IMesh {
	public String getName();
	public boolean onTouch(Vector3d camPos , Vector3d ray, int eventAction);
	
}
