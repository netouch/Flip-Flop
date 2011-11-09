package com.scoreiq;

import java.util.List;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class Menu extends MeshGroup implements IMesh {	
	private Plane background;
	private Vector<Plane>items = new Vector<Plane>();
	
	public Menu(){
	}
	
	public void createMenu(){
		int texId=0;
		background = new Plane(16.0f , 16.0f);
		texId = TextureManager.getInstance().loadTexture("menu_bg.png");
		background.setTextureId(texId);
		background.z += -4.0f;
		
		
	}
	
	@Override
	public void draw(GL10 gl) {
		background.draw(gl);
		for(int i=0; i< items.size();i++){
			items.get(i).draw(gl);
		}
	}

	@Override
	public void update(float secElapsed) {
		// TODO Auto-generated method stub

	}

}
