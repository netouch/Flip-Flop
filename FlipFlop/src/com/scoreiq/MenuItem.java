package com.scoreiq;

public class MenuItem extends Plane {
	GameEvent event;
	float hWidth;
	float hHeight;
	
	public MenuItem(float width , float height, float u_left, float w_down, float u_right, float w_top){
		super(width, height, u_left, w_down , u_right , w_top);
		hWidth = width/2;
		hHeight = height/2;
	}
	
	public void setEvent(GameEvent event){
		this.event = event;
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
