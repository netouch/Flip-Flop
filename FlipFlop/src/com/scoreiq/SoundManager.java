package com.scoreiq;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	private static SoundManager instance;
	
	private Context context;
	
	private SoundPool soundPool;
	private HashMap sounds;
	private AudioManager audioManager;
	
	private SoundManager(){
	}
	
	public static synchronized SoundManager getInstance(){
		if(instance == null) instance = new SoundManager();
		return instance;
	}
	
	public void init(Context cont){
		context = cont;
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		sounds = new HashMap();
		audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public void loadSounds(){
		sounds.put(1, soundPool.load("sounds/flip.mp3", 1));
		sounds.put(2, soundPool.load("sounds/identical.mp3", 1));
	}
	
	public void addSound(int index, String file){
		sounds.put(index, soundPool.load(file, 1));
	}
	
	public void playSound(int index, float speed){
		float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		soundPool.play((Integer) sounds.get(index), streamVolume, streamVolume, 1, 0, speed);
	}
	
	public void stop(int index){
		soundPool.stop((Integer)sounds.get(index));
	}
	
	public void cleaup(){
		soundPool.release();
		soundPool = null;
		
		sounds.clear();
		audioManager.unloadSoundEffects();
		instance = null;
	}
}
