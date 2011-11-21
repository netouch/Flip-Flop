package com.scoreiq;

public interface ITouchNMesh extends IMesh {
	public boolean onTouch(Vector3d camPos , Vector3d ray, int eventAction);
}
