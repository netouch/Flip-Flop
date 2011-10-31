package com.scoreiq;

import javax.microedition.khronos.opengles.GL10;

public interface IMesh {
	public void draw(GL10 gl);
	public void update(float secElapsed);
}
