package com.scoreiq;

import java.io.File;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.R;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class FlipFlopActivity extends Activity{
	private FlipFlopView view;
	private MatrixGrabber mg = new MatrixGrabber();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", String.format("\n\n-------------------------------\nNEW START\nActivity - onCreate()"));
        TextureManager.getInstance().clearInstances();
        TextureManager.getInstance().setActivity(this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.d("TEST", String.format("Activity onCreate() now create View"));
        view = new FlipFlopView(this, mg);
        
        Log.d("TEST", String.format("Activity onCreate() now set wrapper"));
        view.setGLWrapper(new GLSurfaceView.GLWrapper() {
			
			@Override
			public GL wrap(GL gl) {
				return new MatrixTrackingGL(gl);
			}
		});
        
        setContentView(view);
        Log.d("TEST", String.format("Activity onCreate() finished"));
    }
   
    @Override
    public void onPause(){
    	super.onPause();
    	view.onPause();
    	Log.d("TEST", String.format("Activity - onPause()"));
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	TextureManager.getInstance().onStop();
    	Log.d("TEST", String.format("Activity - onStop()"));
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	view.onResume();
    	Log.d("TEST", String.format("Activity - onResume()"));
    }
}