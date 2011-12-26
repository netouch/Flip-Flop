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
	     
	     vibration = (CheckBox)findViewById(R.id.vibrationOn);
	     sound = (CheckBox)findViewById(R.id.soundOn);
	     Button back = (Button)findViewById(R.id.btnBack);
	     
	     setUI();
	    
	     back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent config = new Intent(getApplicationContext(), FlipFlopActivity.class);
				Intent config = new Intent();
				config.putExtra("sound", sound.isChecked());
				config.putExtra("vibration", vibration.isChecked());
				setResult(RESULT_FIRST_USER, config);
				//setResult(RESULT_FIRST_USER);
				finish();
			}
		});
	    
	 }

	private void setUI() {
		Intent i = getIntent();
		if(i!=null){
			sound.setChecked(i.getBooleanExtra("sound", true));
			vibration.setChecked(i.getBooleanExtra("vibration", true));
		}
	}
}
