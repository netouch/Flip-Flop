package com.scoreiq;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class TextureManager {
	private static TextureManager instance;
	
	OGLRenderer renderer;
	private IGameEventListener listener;
	private ArrayList<Integer> textureIds = new ArrayList<Integer>();
	private ArrayList<String> textureNames = new ArrayList<String>();
	private GL10 glInstance = null;
	private Activity act = null;
	
	private int currentTexture = -1;

	private boolean isReady = false;

	private TextureManager(){
	}
	
	public void onStop(){
		textureIds.clear();
		textureNames.clear();
	}
	
	public static synchronized TextureManager getInstance(){
		if(instance==null){
			instance = new TextureManager();
		}
		return instance;
	}

	public int loadTexture(String file){
		int texId[] = new int[1];
		Bitmap btmp=null;

		texId[0]=isTextureLoaded(file);

		if(texId[0]!=-1){
			return texId[0];
		}

		try{
			//BitmapFactory.Options opt = new BitmapFactory.Options();
			//opt.inPreferredConfig = Bitmap.Config.RGB_565;
			
			InputStream in = act.getAssets().open(file);
			//btmp = BitmapFactory.decodeStream(in, null,opt);
			btmp = BitmapFactory.decodeStream(in);
		}
		catch (IOException e){
		}

		glInstance.glGenTextures(1, texId, 0);

		textureNames.add(file);
		textureIds.add(texId[0]);

		glInstance.glBindTexture(GL10.GL_TEXTURE_2D, texId[0]);
		glInstance.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		glInstance.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		/* posible parameters
		   	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
		*/

		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, btmp, 0);
		Log.d("TEST", String.format("TextureManager - texture = %s loaded, ID is %d", file, texId[0]));
		return texId[0];
	}

	private int isTextureLoaded(String file) {
		for(int i=0;i<textureNames.size();i++){
			if(textureNames.get(i).equalsIgnoreCase(file)) return textureIds.get(i);
		}
		return -1;
	}

	public void setGlInstance(GL10 gl){
		glInstance = gl;
		checkReady();
	}
	
	public void setRenderer(OGLRenderer rend){
		renderer = rend;
	}

	public void setActivity(Activity iact){
		act = iact;
		checkReady();
	}
	
	public void clearInstances(){
		glInstance = null;
		act = null;
		checkReady();
	}
	
	public void setListener(IGameEventListener listener){
		this.listener = listener;
	}

	public void checkReady(){
		if(act!=null && glInstance != null){
			isReady = true;
			if(listener!=null){
				listener.onGameEvent(new GameEvent(GameEvent.TEXTURE_MANAGER_READY));
			}
		}
		else{
			isReady = false;
		}
	}

	public void setTexture(int texId){
		if(texId != currentTexture){
			glInstance.glEnable(GL10.GL_TEXTURE_2D);
			glInstance.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			glInstance.glBindTexture(GL10.GL_TEXTURE_2D, texId);
			currentTexture = texId;
		}
	}
	
	private void traceNames(){
		for(int i=0;i<textureNames.size();i++){
			Log.d("TEST", String.format("TextureManager - name trace [%d] = <%s>", i, textureNames.get(i)));
		}
	}
	
	public void loadAllTexturesIn(String path) {
		loadTexture(path+"back.png");
		loadTexture(path+"fr1.png");
		loadTexture(path+"fr2.png");
		loadTexture(path+"fr3.png");
		loadTexture(path+"fr4.png");
		loadTexture(path+"fr5.png");
		loadTexture(path+"fr6.png");
		loadTexture(path+"background.png");
		loadTexture(path+"title.png");
		
		/*
		Log.d("TEST", String.format("TextureManager - loadAllTexturesIn(%s) start", path));
		path = "/";
		String list[];
		try {
			list = act.getAssets().list(path);
			
		} catch (IOException e) {
			Log.d("TEST", String.format("TextureManager - loadAllTexturesIn(%s) failed with IOException", path));
			return;
		}
		
		File fd;
		if(list!=null)
			for(int i=0;i < list.length;i++){
				fd = new File(path, list[i]);
				Log.d("TEST", String.format("TextureManager - loadAllTexturesIn(%s): %s________is dir = %b ",path,  list[i], fd.isDirectory()));
				//loadAllTexturesIn(path+list[i]);
			}
		Log.d("TEST", String.format("TextureManager - loadAllTexturesIn(%s) finish", path));
		*/
	}
}