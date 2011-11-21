package com.scoreiq;

public class MenuItem extends Plane {
	IGameEventListener listener;
	GameEvent event;
	float hWidth;
	float hHeight;
	private int releaseTextureId = -1;
	private int pressTextureId = -1;
	
	public MenuItem(float width , float height, float u_left, float w_down, float u_right, float w_top){
		super(width, height, u_left, w_down , u_right , w_top);
		hWidth = width/2;
		hHeight = height/2;
	}
	
	public void setEvent(GameEvent event){
		this.event = event;
	}
	
	public void setListener(IGameEventListener listener){
		this.listener = listener;
	}
	
	public void dispatchEvent(){
		if(listener!=null)listener.onGameEvent(event);
	}
	
	public void setTextures(int releaseTexId, int pressTexId){
		releaseTextureId = releaseTexId;
		pressTextureId = pressTexId;
		setRelease();
	}
	
	public void setPress(){
		if(pressTextureId != -1)setTextureId(pressTextureId);
	}
	
	public void setRelease(){
		if(releaseTextureId != -1)setTextureId(releaseTextureId);
	}
	
	public boolean isIntersect(float x, float y){
		float left = this.x - hWidth;
		float right = this.x + hWidth;
		float top = this.y+hHeight;
		float bottom = this.y-hHeight;
		
		if(x< right && x>left && y<top && y>bottom)return true;
		else return false;
	}
}
