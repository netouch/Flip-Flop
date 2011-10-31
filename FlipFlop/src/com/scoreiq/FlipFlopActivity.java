package com.scoreiq;

import java.io.File;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL;

import android.R;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class FlipFlopActivity extends Activity {
	private FlipFlopView view;
	private MatrixGrabber mg = new MatrixGrabber();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = new FlipFlopView(this);
        view.setGLWrapper(new GLSurfaceView.GLWrapper() {
			
			@Override
			public GL wrap(GL gl) {
				return new MatrixTrackingGL(gl);
			}
		});
        
        createPads("rio/");
        createCamera();
        
        setContentView(view);
        Log.d("TEST", String.format("NEW START\nActivity created"));
    }

	private void createCamera() {
		Camera cam = new Camera();
		cam.setMatrixGrabber(mg);
		cam.setPosition(0, 0, 15);
		view.setCamera(cam);
	}

	public void createPads(String theme){
    	if(theme == "")theme = "default/";
		
		MeshBuilder builder_top = new MeshBuilder(this);
    	MeshBuilder builder_bottom = new MeshBuilder(this);
    	
    	builder_top.loadObjToClone("pad_top3d.obj");
    	builder_bottom.loadObjToClone("pad_bottom3d.obj");
    	
    	Mesh tmpMesh;
    	Pad tmpPad;
    	
    	int fileNum=1;
    	for(int y=0;y<4;y++)
    		for(int x=0;x<3;x++){
    			tmpPad = new Pad();
    	    	
    	    	tmpMesh = builder_top.cloneMesh();
    	    	tmpMesh.loadBitmapFromFile(theme+"fr"+fileNum+".png", this);
    	    	tmpPad.addMesh(tmpMesh);
    	    	
    	    	tmpMesh = builder_bottom.cloneMesh();
    	    	tmpMesh.loadBitmapFromFile(theme+"back.png", this);
    	    	tmpPad.addMesh(tmpMesh);
    	    	
    	    	tmpPad.Rotate(270, 1);
    	    	tmpPad.x += -2.5+x*2.5;
    	    	tmpPad.y += 4.0-y*2.7;
    	    	
    	    	view.addPad(tmpPad);
    	    	
    	    	fileNum++;
    	    	if(fileNum>6)fileNum=1;
    		}
    	
    	Plane plane = new Plane(16.0f , 16.0f);
    	plane.z += -4.0f;
    	plane.loadBitmapFromFile(theme+"background.png", this);
    	view.addToVisible(plane);
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    }

}