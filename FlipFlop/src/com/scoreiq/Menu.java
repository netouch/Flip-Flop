package com.scoreiq;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Menu extends MeshGroup implements ITouchNMesh, IGameEventListener {	
	private static final int MAINMENU = 0;
	private static final int THEMEMENU = 1;
	
	
	private IGameEventListener listener;
	
	private Plane background;
	private int currentMenuGroup = MAINMENU;
	private ArrayList<Vector> menuGroups = new ArrayList<Vector>();
	//private Vector<MenuItem>items = new Vector<MenuItem>();
	
	public Menu(){
		Log.d("TEST", String.format("Menu - Menu() constructor"));
		menuGroups.add(new Vector<MenuItem>());
		menuGroups.add(new Vector<MenuItem>());
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
		item.setListener(this);
		item.y += 3.0f;
		menuGroups.get(MAINMENU).add(item);
		
		item = new MenuItem(6.0f, 2.0f, 0.0f, 0.25f, 1.0f, 0.5f);
		item.setTextureId(TextureManager.getInstance().loadTexture("menu.png"));
		item.setEvent(new GameEvent(GameEvent.MENU_THEME));
		item.setListener(this);
		item.y += -1.0f;
		menuGroups.get(MAINMENU).add(item);
		
		//Now create theme menu
		item = new MenuItem(3.0f, 3.0f, 0.0f, 0.0f, 1.0f, 1.0f);
		item.setTextureId(TextureManager.getInstance().loadTexture("rio/title.png"));
		item.setEvent(new GameEvent(GameEvent.THEME_SELECT , "rio/"));
		item.setListener(this);
		item.x += -2.0f;
		menuGroups.get(THEMEMENU).add(item);
		
		item = new MenuItem(3.0f, 3.0f, 0.0f, 0.0f, 1.0f, 1.0f);
		item.setTextureId(TextureManager.getInstance().loadTexture("default/title.png"));
		item.setEvent(new GameEvent(GameEvent.THEME_SELECT, "default/"));
		item.setListener(this);
		item.x += 2.0f;
		menuGroups.get(THEMEMENU).add(item);
		
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
		
		Vector<MenuItem> items = menuGroups.get(currentMenuGroup);
		
		for(int i=0;i<items.size();i++){
			if(items.get(i).isIntersect(x, y)){
				Log.d("TEST", String.format(" --> Index of picked menuItem is %d in menuGroup #%d", i, currentMenuGroup));
				//if(listener!=null)listener.onGameEvent(items.get(i).event);
				items.get(i).dispatchEvent();
				}
		}
		return true;
	}
	
	@Override
	public void draw(GL10 gl) {
		if(background!=null)background.draw(gl);
		/*
		for(int i=0; i< items.size();i++){
			items.get(i).draw(gl);
		}
		*/
		MenuItem mi;
		for(int i=0;i< menuGroups.get(currentMenuGroup).size() ; i++){
			mi = (MenuItem)menuGroups.get(currentMenuGroup).get(i);
			mi.draw(gl);
		}
	}

	@Override
	public void update(float secElapsed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameEvent(GameEvent event) {
		switch(event.type){
		case GameEvent.MENU_START:
			if(listener!=null)listener.onGameEvent(event);
			break;
		case GameEvent.MENU_THEME:
			currentMenuGroup = THEMEMENU;
			break;
		case GameEvent.THEME_SELECT:
			currentMenuGroup = MAINMENU;
			break;
		}
	}
}
