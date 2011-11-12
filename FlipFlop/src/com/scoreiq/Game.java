package com.scoreiq;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;

public class Game implements ITouchNMesh {
	private static final int NO_PAD = -1;
	
	private Activity act;
	
	private Vector<Pad> pads = new Vector<Pad>();
	private Plane background;
	
	private int flipedPadImage = NO_PAD;
	 
	public Game(Activity act){
		this.act = act;
	}
	
	public void loadLevel(String theme){
		if(theme == "")theme = "default/";
		
		MeshBuilder builder_top = new MeshBuilder(act);
    	MeshBuilder builder_bottom = new MeshBuilder(act);
    	TextureManager tm = TextureManager.getInstance();
    	
    	builder_top.loadObjToClone("pad_top3d.obj");
    	builder_bottom.loadObjToClone("pad_bottom3d.obj");
    	
    	Mesh tmpMesh;
    	Pad tmpPad;
    	
    	int fileNum=1;
    	for(int y=0;y<4;y++)
    		for(int x=0;x<3;x++){
    			//Log.d("TEST", String.format("-------------------------------\ncreatePads() - Enter"));
    			tmpPad = new Pad();
    	    	
    	    	tmpMesh = builder_top.cloneMesh();
    	    	//tmpMesh.loadBitmapFromFile(theme+"fr"+fileNum+".png", this);
    	    	tmpMesh.setTextureId(tm.loadTexture(theme+"fr"+fileNum+".png"));
    	    	tmpPad.addMesh(tmpMesh);
    	    	//Log.d("TEST", String.format("createPads() - top cloned"));
    	    	
    	    	tmpMesh = builder_bottom.cloneMesh();
    	    	//tmpMesh.loadBitmapFromFile(theme+"back.png", this);
    	    	tmpMesh.setTextureId(tm.loadTexture(theme+"back.png"));
    	    	tmpPad.addMesh(tmpMesh);
    	    	tmpPad.faceImageId = fileNum;
    	    	//Log.d("TEST", String.format("createPads() - bottomcloned"));
    	    	
    	    	tmpPad.Rotate(270, 0.5f);
    	    	tmpPad.x += -2.5+x*2.5;
    	    	tmpPad.y += 4.0-y*2.7;
    	    	//Log.d("TEST", String.format("createPads() - Pad's position setted"));
    	    	
    	    	pads.add(tmpPad);
    	    	//Log.d("TEST", String.format("createPads() - Pad added"));
    	    	
    	    	fileNum++;
    	    	if(fileNum>6)fileNum=1;
    		}
    	shufflePads();
    	
    	background = new Plane(16.0f , 16.0f);
    	background.z += -4.0f;
    	//plane.loadBitmapFromFile(theme+"background.png", this);
    	background.setTextureId(tm.loadTexture(theme+"background.png"));
	}
	
	private void shufflePads() {
		int padsNum = pads.size();
		Vector3d tmpPos;
		
		for(int i=0 ; i < padsNum ; i++){
			int rndPad = (int)(Math.random()*padsNum);
			tmpPos = pads.get(i).getPosition();
			pads.get(i).setPosition(pads.get(rndPad).getPosition());
			pads.get(rndPad).setPosition(tmpPos);
		}
	}
	
	public boolean onTouch(Vector3d camPos , Vector3d ray){
		int i = getTapedPadNum(camPos,ray);
		if(i != NO_PAD)
			if(!pads.get(i).isFlipping()){
				if(flipedPadImage == NO_PAD){
					//pads.get(i).Rotate(180, 1);
					pads.get(i).flip();
					flipedPadImage = i;
				}
				else checkPadIdentity(i); 
			}
		
		return true;
	}
	
	private void checkPadIdentity(int i) {
		if(flipedPadImage == i){
			pads.get(flipedPadImage).isActive = false;
			pads.get(i).isActive = false;
		}
		else{
			pads.get(flipedPadImage).flip();
			pads.get(i).flip();
		}
		flipedPadImage = NO_PAD;
	}

	private int getTapedPadNum(Vector3d camPos , Vector3d ray) {
		Log.d("TEST", String.format("Calculate touched pad-------------------"));
		float x;
		float y;
		//Vector3d pos = camera.getPosition();
		float multipliyer = camPos.z/Math.abs(ray.z);
		Log.d("TEST", String.format("- multipliyer = %f", multipliyer));
		x=camPos.x + ray.x*multipliyer;
		y=camPos.y + ray.y*multipliyer;
		Log.d("TEST", String.format("- x;y = %f;%f", x,y));
		for(int i=0;i<pads.size();i++){
			if(pads.get(i).isIntersect(x, y) && !pads.get(i).fliped){
				Log.d("TEST", String.format(" --> Index of picked Pad is %d", i));
				return i;
				}
		}
		return NO_PAD;
	}

	@Override
	public void draw(GL10 gl) {
		if(background != null)background.draw(gl);
		
		Pad tmpPad;
		for(int i=0;i<pads.size();i++){
			tmpPad = pads.get(i);
			if(tmpPad.isActive)tmpPad.draw(gl);
		}
	}

	@Override
	public void update(float secElapsed) {
		Pad tmpPad;
		for(int i=0;i<pads.size();i++){
			tmpPad = pads.get(i);
			if(tmpPad.isActive)tmpPad.update(secElapsed);
		}
	}

}
