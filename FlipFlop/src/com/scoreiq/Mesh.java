package com.scoreiq;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Mesh implements IMesh{
	private FloatBuffer verticesBuffer = null;
	private ShortBuffer indicesBuffer = null;
	private int numOfIndices = -1;
	
	private float[] rgba = new float[]{1.0f , 1.0f , 1.0f , 1.0f};
	private FloatBuffer colorBuffer = null;
	//members for texture
	private FloatBuffer mUVTextureBuffer = null;
	private int mTextureId = -1;
	private Bitmap mBitmap = null;
	private boolean mShouldLoadTexture = false;
	
	public Vector3d position;
	public Vector3d posSpeed;
	public Vector3d posAcceleration;
	
	public Vector3d rotation;
	public Vector3d rotSpeed;
	public Vector3d rotAcceleration;
	
	public Vector3d scale;
	
	
	public float x = 0.0f;
	public float y = 0.0f;
	public float z = 0.0f;
	
	public float rx = 0.0f;
	public float ry = 0.0f;
	public float rz = 0.0f;
	
	public Mesh(){
		position = new Vector3d(0.0f , 0.0f , 0.0f);
		posSpeed = new Vector3d(0.0f , 0.0f , 0.0f);
		posAcceleration = new Vector3d(0.0f , 0.0f , 0.0f);
		rotation = new Vector3d(0.0f , 0.0f , 0.0f);
		rotSpeed = new Vector3d(0.0f , 0.0f , 0.0f);
		rotAcceleration = new Vector3d(0.0f , 0.0f , 0.0f);
		scale = new Vector3d(1.0f , 1.0f , 1.0f);
	}
	
	public void setTextureCoordinates(float textureCoordinates[]){
		ByteBuffer byteBuff = ByteBuffer.allocateDirect(textureCoordinates.length*4);
		byteBuff.order(ByteOrder.nativeOrder());
		mUVTextureBuffer = byteBuff.asFloatBuffer();
		mUVTextureBuffer.put(textureCoordinates);
		mUVTextureBuffer.position(0);
	}
	
	public void setTextureId(int texId){
		mTextureId = texId;
	}
	
	//TODO: refactor to return position;
	public Vector3d getPosition(){
		return new Vector3d(x,y,z);
	}
	
	public void setPosition(Vector3d pos){
		x = pos.x;
		y = pos.y;
		z = pos.z;
		
		position = pos;
	}
	
	public void setPosAcceleration(Vector3d posAcceleration) {
		this.posAcceleration = posAcceleration;
	}
	
	public void setPosSpeed(Vector3d posSpeed) {
		this.posSpeed = posSpeed;
	}
	
	public void setRotAcceleration(Vector3d rotAcceleration) {
		this.rotAcceleration = rotAcceleration;
	}
	
	public void setRotSpeed(Vector3d rotSpeed) {
		this.rotSpeed = rotSpeed;
	}
	
	public void loadBitmap(Bitmap btmp){
		//bitmap = BitmapFactory.decodeResource(Context.getResources(), R.drawable.icon);
		mBitmap = btmp;
		mShouldLoadTexture = true;
	}
	
	public void loadBitmapFromFile(String file, Activity act){
		Bitmap btmp=null;
		//btmp = .....
		try{
			InputStream in = act.getAssets().open(file);
			btmp = BitmapFactory.decodeStream(in);
		}
		catch (IOException e){
		}
		
		if(btmp!=null)
			loadBitmap(btmp);
	}
	
	private void loadTexture(GL10 gl){
		int textures[] = new int[1];
		//Log.d("TEST", String.format("not generated texture = %d", mTextureId));
		gl.glGenTextures(1, textures, 0);
		mTextureId = textures[0];
		//Log.d("TEST", String.format("generated texture = %d", mTextureId));
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		/* posible parameters
		   	gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);
		*/
		
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
	}
	
	public void draw(GL10 gl) {
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW);
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE);
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK);
		// Enabled the vertices buffer for writing and to be used during
		// rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
	    // Set flat color
	    gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
	    // Smooth color
	    if(colorBuffer != null){
	        // Enable the color array buffer to be used during rendering.
	        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	        // Point out the where the color buffer is.
	        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
	    }
	    
	    if(mTextureId != -1 && mUVTextureBuffer != null){
	    	gl.glEnable(GL10.GL_TEXTURE_2D);
	    	gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    	
	    	gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mUVTextureBuffer);
	    	gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
	    }
	    
	    gl.glPushMatrix();
	    
	    /*
	    gl.glTranslatef(x, y, z);
	    gl.glRotatef(rx, 1, 0, 0);
	    gl.glRotatef(ry, 0, 1, 0);
	    gl.glRotatef(rz, 0, 0, 1);
	    */
	    
	    gl.glTranslatef(position.x, position.y, position.z);
	    gl.glRotatef(rotation.x, 1, 0, 0);
	    gl.glRotatef(rotation.y, 0, 1, 0);
	    gl.glRotatef(rotation.z, 0, 0, 1);
	    gl.glScalef(scale.x, scale.y, scale.z);
	    
	    
		gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices,
			GL10.GL_UNSIGNED_SHORT, indicesBuffer);
		
		gl.glPopMatrix();
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		
		if(mTextureId != -1 && mUVTextureBuffer != null){
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE);
}
	
	public void update(float secElapsed){
		posSpeed = posSpeed.add(posAcceleration);
		position = position.add(posSpeed.multiply(secElapsed));
		
		rotSpeed = rotSpeed.add(rotAcceleration);
		rotation = rotation.add(rotSpeed.multiply(secElapsed));
	}
	
	public void setVertices(float[] vertices){
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
		vbb.order(ByteOrder.nativeOrder());
		verticesBuffer = vbb.asFloatBuffer();
		verticesBuffer.put(vertices);
		verticesBuffer.position(0);
	}
	
	
	public void setIndices(short[] indices){
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length*2);
		ibb.order(ByteOrder.nativeOrder());
		indicesBuffer = ibb.asShortBuffer();
		indicesBuffer.put(indices);
		indicesBuffer.position(0);
		numOfIndices = indices.length;
	}

	protected void setColor(float r, float g , float b, float a){
		rgba[0] = r;
		rgba[1] = g;
		rgba[2] = b;
		rgba[3] = a;
	}
	
	protected void setColors(float[] colors){
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}	
}
