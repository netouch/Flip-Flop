package scoreiq.com;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class FlipFlopActivity extends Activity {
	FlipFlopView view;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        view = new FlipFlopView(this);
        createPads();
        
        setContentView(view);
    }
    
    public void createPads(){
    	MeshBuilder builder = new MeshBuilder(this);
    	Pad tmpPad = new Pad();
    	Mesh tmpMesh;
    	
    	tmpMesh = builder.createMesh("top.obj");
    	tmpMesh.loadBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon));
    	tmpPad.addMesh(tmpMesh);
    	tmpMesh = builder.createMesh("bottom.obj");
    	tmpPad.addMesh(tmpMesh);
    	
    	view.padList.addMesh(tmpPad);
    }
    
    @Override
    public void onPause(){
    }
    
    @Override
    public void onStop(){
    }
    
    @Override
    public void onResume(){
    	
    }
}