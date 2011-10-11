package scoreiq.com;

import android.app.Activity;
import android.os.Bundle;

public class FlipFlopActivity extends Activity {
	FlipFlopView view;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        view = new FlipFlopView(this);
        setContentView(view);
    }
    
    @Override
    public void onPause(){
    }
    
    @Override
    public void onStop(){
    }
}