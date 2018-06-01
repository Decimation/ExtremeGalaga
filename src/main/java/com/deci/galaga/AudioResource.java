package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;

class AudioResource extends Resource {


	private static boolean      tryToInterruptSound = false;
	private static long         mainTimeOut         = 3000;
	private static long         startTime           = System.currentTimeMillis();
	private        int          length;
	@Setter(AccessLevel.PACKAGE)
	@Getter(AccessLevel.PACKAGE)
	private        float        volume;
	private        Clip         clip;
	private        AudioFormat  format;

	AudioResource(String path, String fileName) {
		this(path, fileName, ResourceType.URL);
	}

	AudioResource(String path, String fileName, ResourceType type) {
		super(path, fileName);

		volume = -10f;
		AudioInputStream stream = null;
		switch (type) {
			case URL:
				try {
					URL u = new URL(getFullPath());
					stream = AudioSystem.getAudioInputStream(u);
					length = Common.getFileSize(u);
				} catch (Exception x) {
					x.printStackTrace();
				}
				break;
			case FILE:
				try {
					File f = new File(getFullPath());
					stream = AudioSystem.getAudioInputStream(f);
					length = (int) f.length();
				} catch (Exception x) {
					x.printStackTrace();
				}
				break;
		}
		try {
			clip = AudioSystem.getClip();
			clip.open(stream);
			format = stream.getFormat();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	synchronized void play() {
		play(-10f);
	}

	//todo: fix choppy sounds and FIGURE OUT A MORE EFFICIENT WAY TO PLAY SOUNDS
	synchronized void play(float db) {

		Thread snd = new Thread(() -> {

			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			long durationInMilliSeconds =
					(long) (((float) length / (frameSize * frameRate)) * 1000);

			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

			//float range = gainControl.getMaximum() - gainControl.getMinimum();
			//float gain = (range * db) + gainControl.getMinimum();
			gainControl.setValue(db);

			if (clip.isRunning()) {
				//clip.stop();
			}
			clip.setFramePosition(0);
			clip.start();
			Common.printf(Debug.SOUND, "%d: sound started", System.currentTimeMillis() - startTime);

			Common.sleep(durationInMilliSeconds);
			while (true) {
				if (!clip.isActive()) {
					Common.printf(Debug.SOUND, "%d: sound completed", System.currentTimeMillis() - startTime);
					break;
				}
				long fPos = (clip.getMicrosecondPosition() / 1000);
				long left = durationInMilliSeconds - fPos;
				Common.printf(Debug.SOUND, "%d: time left: %d", System.currentTimeMillis() - startTime, left);
				if (left > 0) Common.sleep(left);
			}
			clip.stop();
			Common.printf(Debug.SOUND, "%d: sound stopped", System.currentTimeMillis() - startTime);

			//clip.drain();
			//clip.close();
		});
		snd.setDaemon(true);
		snd.start();
	}


}
