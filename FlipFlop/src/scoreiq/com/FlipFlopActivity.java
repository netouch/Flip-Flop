package scoreiq.com;

import java.io.File;
import java.io.InputStream;

import android.R;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class FlipFlopActivity extends Activity {
	FlipFlopView view;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        view = new FlipFlopView(this);
        createPads();
        
        setContentView(view);
        Log.d("TEST", String.format("NEW START\nActivity created"));
    }
    
    public void createPads(){
    	MeshBuilder builder = new MeshBuilder(this);
    	Pad tmpPad = new Pad();
    	Mesh tmpMesh;
    	
    	tmpMesh = builder.createMesh("pad_top.obj");
    	tmpMesh.loadBitmapFromFile("fr1.png", this);
    	tmpPad.addMesh(tmpMesh);
    	
    	tmpMesh = builder.createMesh("pad_bottom.obj");
    	tmpMesh.loadBitmapFromFile("back.png", this);
    	
    	tmpPad.addMesh(tmpMesh);
    	
    	tmpPad.Rotate(180, 1);
    	tmpPad.x += -2.2;
 
    	view.addPad(tmpPad);
    	/////////////
    	tmpPad = new Pad();
    	tmpMesh = builder.createMesh("pad_top.obj");
    	tmpMesh.loadBitmapFromFile("fr2.png", this);
    	tmpPad.addMesh(tmpMesh);
    	
    	tmpMesh = builder.createMesh("pad_bottom.obj");
    	tmpMesh.loadBitmapFromFile("back.png", this);
    	
    	tmpPad.addMesh(tmpMesh);
    	
    	tmpPad.Rotate(180, 1);
    	tmpPad.x += 0;
 
    	view.addPad(tmpPad);
    	//////////////////////////
    	tmpPad = new Pad();
    	tmpMesh = builder.createMesh("pad_top.obj");
    	tmpMesh.loadBitmapFromFile("fr3.png", this);
    	tmpPad.addMesh(tmpMesh);
    	
    	tmpMesh = builder.createMesh("pad_bottom.obj");
    	tmpMesh.loadBitmapFromFile("back.png", this);
    	
    	tmpPad.addMesh(tmpMesh);
    	
    	tmpPad.Rotate(180, 1);
    	tmpPad.x += 2.2;
 
    	view.addPad(tmpPad);
    	
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