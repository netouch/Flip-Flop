package com.scoreiq;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

public class VibratorManager {
	private static VibratorManager instance;
	private Context context;
	private Vibrator vibrator;
	private boolean enabled = true;
	
	private VibratorManager() {
	}
	
	public static synchronized VibratorManager getInstance(){
		if(instance == null) instance = new VibratorManager();
		return instance;
	}
	
	public void init(Activity act){
		this.context = context;
		if(vibrator == null)
			vibrator = (Vibrator)act.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	public void setEnable(boolean en)
	{
		enabled = en;
	}
	
	public boolean getEnable(){
		return enabled;
	}
	
	public void vibrate(){
		if(enabled && vibrator != null) vibrator.vibrate(200);
	}
	
}
