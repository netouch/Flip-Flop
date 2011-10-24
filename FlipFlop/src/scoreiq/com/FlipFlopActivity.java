package scoreiq.com;

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
    	//tmpMesh.loadBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon));
 
    	tmpPad.addMesh(tmpMesh);
    	tmpMesh = builder.createMesh("pad_bottom.obj");
    	tmpPad.addMesh(tmpMesh);
    	
    	tmpPad.Rotate(5);
    	
    	view.padList.addMesh(tmpPad);
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