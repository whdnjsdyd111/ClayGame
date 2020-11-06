package main.resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audios {
	private AudioInputStream shoot = null;
	private AudioInputStream reload = null;
	private AudioInputStream percus = null;
	private Clip shoot_clip = null;
	private Clip reload_clip = null;
	private Clip percus_clip = null;
	public Audios() {
		try {
			shoot = AudioSystem.getAudioInputStream(getClass().getResource("shoot.wav"));
			reload = AudioSystem.getAudioInputStream(getClass().getResource("reload.wav"));
			percus = AudioSystem.getAudioInputStream(getClass().getResource("percussion.wav"));
			shoot_clip = AudioSystem.getClip();
			shoot_clip.open(shoot);
			reload_clip = AudioSystem.getClip();
			reload_clip.open(reload);
			percus_clip = AudioSystem.getClip();
			percus_clip.open(percus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void shoot() {
		shoot_clip.start();
	}
	
	public void reload() {
		reload_clip.start();
	}
	
	public void percus() {
		percus_clip.start();
	}
	
}
