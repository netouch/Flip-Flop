package com.scoreiq;

import java.util.List;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Menu extends MeshGroup implements ITouchNMesh {	
	private IGameEventListener listener;
	
	private Plane background;
	private Vector<MenuItem>items = new Vector<MenuItem>();
	
	public Menu(){
		Log.d("TEST", String.format("Menu - Menu() constructor"));
	}
	
	public void createMenu(){
		Log.d("TEST", String.format("Menu - createMenu() start"));
		int texId=0;
		MenuItem item;
		
		background = new Plane(16.0f , 16.0f);
		texId = TextureManager.getInstance().loadTexture("menu_bg.png");
		background.setTextureId(texId);
		background.z += -4.0f;
		
		item = new MenuItem(6.0f, 2.0f, 0.0f, 0.0f, 1.0f, 0.25f);
		item.setTextureId(TextureManager.getInstance().loadTexture("menu.png"));
		item.setEvent(new GameEvent(GameEvent.MENU_START));
		item.y += 3.0f;
		items.add(item);
		
		item = new MenuItem(6.0f, 2.0f, 0.0f, 0.25f, 1.0f, 0.5f);
		item.setTextureId(TextureManager.getInstance().loadTexture("menu.png"));
		item.setEvent(new GameEvent(GameEvent.MENU_THEME));
		item.y += -1.0f;
		items.add(item);
		
		Log.d("TEST", String.format("Menu - createMenu() finish"));
	}
	
	public void setListener(IGameEventListener lnr){
		listener = lnr;
	}
	
	public boolean onTouch(Vector3d camPos , Vector3d ray){
		Log.d("TEST", String.format("Calculate touched menuItem-------------------"));
		float x;
		float y;

		float multipliyer = camPos.z/Math.abs(ray.z);
		Log.d("TEST", String.format("- multipliyer = %f", multipliyer));
		x=camPos.x + ray.x*multipliyer;
		y=camPos.y + ray.y*multipliyer;
		Log.d("TEST", String.format("- x:y  at z=0 = %f;%f", x,y));
		for(int i=0;i<items.size();i++){
			if(items.get(i).isIntersect(x, y)){
				Log.d("TEST", String.format(" --> Index of picked menuItem is %d", i));
				listener.onGameEvent(items.get(i).event);
				}
		}
		return true;
	}
	
	@Override
	public void draw(GL10 gl) {
		if(background!=null)background.draw(gl);
		for(int i=0; i< items.size();i++){
			items.get(i).draw(gl);
		}
	}

	@Override
	public void update(float secElapsed) {
		// TODO Auto-generated method stub

	}

}
