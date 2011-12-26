package com.scoreiq;

import javax.microedition.khronos.opengles.GL;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.opengl.GLSurfaceView;
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
        
        SoundManager.getInstance();
        SoundManager.getInstance().init(this);
        SoundManager.getInstance().loadSounds();
        
        VibratorManager.getInstance().init(this);
        
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Log.d("TEST", String.format("Activity onCreate() now create View"));
        view = new FlipFlopView(this, mg);
        
        //Log.d("TEST", String.format("Activity onCreate() now set wrapper"));
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
    public void onBackPressed(){
    	Log.d("TEST", String.format("Activity - onBackPressed() - I got BACK, need return to MENU or QUIT"));
    	if(view.gameManager.isMenuState()){
    		onStop();
        	this.finish();
    	}
    	else view.gameManager.onGameEvent(new GameEvent(GameEvent.GAME_END));
    	
    	return;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i){
    	Log.d("TEST", String.format("Activity - onActivityResult() - requestCode=%d, resultCode=%d", requestCode, resultCode));
    	if(resultCode == RESULT_FIRST_USER){
    		boolean sound = i.getBooleanExtra("sound", true);
	    	boolean vibration = i.getBooleanExtra("vibration", true);
	    	Log.d("TEST", String.format("Activity - onActivityResult() - sound=%b, vibration=%b", sound, vibration));
	    	SoundManager.getInstance().setSoundOn(sound);
	    	VibratorManager.getInstance().setEnable(vibration);
	    }
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
    	//SoundManager.getInstance().cleaup();
    	Log.d("TEST", String.format("Activity - onStop()"));
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	view.onResume();
    	Log.d("TEST", String.format("Activity - onResume()"));
    }
}