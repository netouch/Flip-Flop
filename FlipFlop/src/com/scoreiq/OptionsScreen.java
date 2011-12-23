package com.scoreiq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class OptionsScreen extends Activity {
	CheckBox vibration;
	CheckBox sound;
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.options);
	     
	     //vibration = (CheckBox)findViewById(R.id.vibrationOn);
	     //sound = (CheckBox)findViewById(R.id.soundOn);
	     Button back = (Button)findViewById(R.id.btnBack);
	     
/*	     receive data
	     Intent i = getIntent();
	     String nm=null;
	     String em=null;
	     if(i!=null){
	    	 nm = i.getStringExtra("name");
	    	 em = i.getStringExtra("email");
	     }
	     
	     if(nm !=null && em!=null){
	     	name.setText(nm);
	     	email.setText(em);
	     }
*/	     
	     
/* sending data
 Intent nextScreen = new Intent(getApplicationContext(), OptionsScreen.class);
				
				nextScreen.putExtra("name", name.getText().toString());
				nextScreen.putExtra("email", email.getText().toString());
				Log.d("TEST", String.format("Sending %s:%s", name.getText().toString(), email.getText().toString() ));
				
				startActivity(nextScreen);
 * 
 */
	     
	    
	     back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent config = new Intent(getApplicationContext(), FlipFlopActivity.class);
				//config.putExtra("sound", sound.isChecked());
				//config.putExtra("vibration", vibration.isChecked());
				//setResult(RESULT_OK, config);
				finish();
			}
		});
	    
	 }
}
