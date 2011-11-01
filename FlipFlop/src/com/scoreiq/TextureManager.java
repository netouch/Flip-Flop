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
	private static TextureManager instance = new TextureManager();

	private ArrayList<Integer> TextureIds = new ArrayList<Integer>();
	private ArrayList<String> TextureNames = new ArrayList<String>();
	private GL10 glInstance = null;
	private Activity act = null;

	private boolean isReady = false;

	public TextureManager(){
	}

	public static TextureManager getInstance(){
		return instance;
	}

	public int loadTexture(String file){
		int texId[] = new int[1];
		Bitmap btmp=null;

		texId[0]=isTextureLoaded(file);

		if(texId[0]!=0){
			Log.d("TEST", String.format("TextureManager - texture = %s is already loaded and ID is %d", file, texId[0]));
			return texId[0];
		}

		try{
			InputStream in = act.getAssets().open(file);
			btmp = BitmapFactory.decodeStream(in);
		}
		catch (IOException e){
		}

		//Log.d("TEST", String.format("not generated texture = %d", mTextureId));
		glInstance.glGenTextures(1, texId, 0);

		TextureNames.add(file);
		TextureIds.add(texId[0]);
		//Log.d("TEST", String.format("generated texture = %d", mTextureId));
		glInstance.glBindTexture(GL10.GL_TEXTURE_2D, texId[0]);
		glInstance.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		glInstance.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		/* posible parameters
		   	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
		*/

		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, btmp, 0);

		return texId[0];
	}

	private int isTextureLoaded(String file) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setGlInstance(GL10 gl){
		glInstance = gl;
		checkReady();
	}

	public void setActivity(Activity iact){
		act = iact;
		checkReady();
	}

	public void checkReady(){
		if(act!=null && glInstance != null)isReady = true;
	}

	public void setTexture(int texId){
		glInstance.glEnable(GL10.GL_TEXTURE_2D);
		glInstance.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		//glInstance.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mUVTextureBuffer);
		glInstance.glBindTexture(GL10.GL_TEXTURE_2D, texId);
	}
}

