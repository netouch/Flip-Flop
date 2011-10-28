package scoreiq.com;

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
        
        createPads();
        createCamera();
        
        setContentView(view);
        Log.d("TEST", String.format("NEW START\nActivity created"));
    }
    
    private void createCamera() {
		// TODO Auto-generated method stub
		Camera cam = new Camera();
		cam.setMatrixGrabber(mg);
		cam.setPosition(0, 0, 15);
		view.setCamera(cam);
	}

	public void createPads(){
    	MeshBuilder builder_top = new MeshBuilder(this);
    	MeshBuilder builder_bottom = new MeshBuilder(this);
    	
    	builder_top.loadObjToClone("pad_top.obj");
    	builder_bottom.loadObjToClone("pad_bottom.obj");
    	
    	Mesh tmpMesh;
    	Pad tmpPad;
    	
    	for(int y=0;y<4;y++)
    		for(int x=0;x<3;x++){
    			tmpPad = new Pad();
    	    	
    	    	tmpMesh = builder_top.cloneMesh();
    	    	tmpMesh.loadBitmapFromFile("fr1.png", this);
    	    	tmpPad.addMesh(tmpMesh);
    	    	
    	    	tmpMesh = builder_bottom.cloneMesh();
    	    	tmpMesh.loadBitmapFromFile("back.png", this);
    	    	tmpPad.addMesh(tmpMesh);
    	    	
    	    	tmpPad.Rotate(90, 1);
    	    	tmpPad.x += -2.5+x*2.5;
    	    	tmpPad.y += 4.0-y*2.7;
    	    	
    	    	view.addPad(tmpPad);
    		}
    }
/*    
    @Override
    public void onPause(){
    }
    
    @Override
    public void onStop(){
    }
    
    @Override
    public void onResume(){
    	
    }
*/
}