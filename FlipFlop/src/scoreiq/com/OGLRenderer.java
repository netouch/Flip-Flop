package scoreiq.com;

import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;
import android.util.Log;

import java.util.Date;

public class OGLRenderer implements Renderer {
	long prevTime = 0;
	long curTime = 0;
	
	Mesh root = null;
	
	public OGLRenderer(){
		prevTime = curTime = System.currentTimeMillis();
		Log.d("TEST", String.format("Render. created"));
	}
	
	public void setDrawing(Mesh m_root){
		root = m_root;
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//TODO: fix 800x400 later
		//GLU.gluOrtho2D(gl, 0, 800, 0, 400);
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		Log.d("TEST", String.format("Render.OnSurfaceCreated complete"));
	}

	public void onDrawFrame(GL10 gl) {
		curTime = System.currentTimeMillis();
		
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// Replace the current matrix with the identity matrix
		//TODO: del this
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -6.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		gl.glRotatef(90, 1, 0, 0);
		
		// Draw.
		if(root!=null){
			root.update((float)(curTime - prevTime)/1000);
			root.draw(gl);
		}
		
		prevTime = curTime;
		//Log.d("TEST", String.format("Render.onDrawFrame %f", (float)((curTime - prevTime)/1000)));
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		//TODO: fix later
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,	100.0f);
		//GLU.gluOrtho2D(gl, 0, width, 0, height);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
		
	}
}
