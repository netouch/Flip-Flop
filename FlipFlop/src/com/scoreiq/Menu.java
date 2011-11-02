package com.scoreiq;

import javax.microedition.khronos.opengles.GL10;

public class Menu extends MeshGroup implements IMesh {	
	
	public Menu(){
		
	}
	
	public void createMenu(){
		Plane background = new Plane(16.0f , 16.0f);
		// тут через менеджер текстур нужно загрузить фон
		//background
		
		background.z += -4.0f;
		
	}
	
	@Override
	public void draw(GL10 gl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float secElapsed) {
		// TODO Auto-generated method stub

	}

}
