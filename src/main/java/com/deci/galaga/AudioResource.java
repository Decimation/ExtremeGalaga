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
	private FloatControl control;
	private AudioInputStream stream;


	AudioResource(String path, String fileName) {
		this(path, fileName, ResourceType.URL);
	}

	AudioResource(String path, String fileName, ResourceType type) {
		super(path, fileName);

		volume = -10f;
		//AudioInputStream stream = null;
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

	synchronized void close() {
		clip.close();
		try {
			stream.close();
		} catch (Exception x) {
			x.printStackTrace();
		}
		Common.printf(Debug.SOUND, "Closing");
	}

	synchronized void play() {
		play(-10f);
	}

	private static class SoundThread implements Runnable {

		private final AudioResource res;
		private final float db;

		SoundThread(final AudioResource res, final float db) {
			this.res = res;
			this.db = db;
		}

		@Override
		public void run() {
			int frameSize = res.format.getFrameSize();
			float frameRate = res.format.getFrameRate();
			res.control = (FloatControl)  res.clip.getControl(FloatControl.Type.MASTER_GAIN);
			long durationInMilliSeconds =
					(long) (((float) res.length / (frameSize * frameRate)) * 1000);



			//float range = gainControl.getMaximum() - gainControl.getMinimum();
			//float gain = (range * db) + gainControl.getMinimum();
			res.control.setValue(db);

			if (res.clip.isRunning()) {
				res.clip.stop();
			}
			res.clip.stop();
			res.clip.setFramePosition(0);
			res.clip.start();
			Common.printf(Debug.SOUND, "%d: sound started", System.currentTimeMillis() - startTime);

			Common.sleep(durationInMilliSeconds);
			while (true) {
				if (!res.clip.isActive()) {
					Common.printf(Debug.SOUND, "%d: sound completed", System.currentTimeMillis() - startTime);
					break;
				}
				long fPos = (res.clip.getMicrosecondPosition() / 1000);
				long left = durationInMilliSeconds - fPos;
				Common.printf(Debug.SOUND, "%d: time left: %d", System.currentTimeMillis() - startTime, left);
				if (left > 0) Common.sleep(left);
			}
			res.clip.stop();
			Common.printf(Debug.SOUND, "%d: sound stopped", System.currentTimeMillis() - startTime);
		}
	}

	//todo: fix choppy sounds and FIGURE OUT A MORE EFFICIENT WAY TO PLAY SOUNDS
	synchronized void play(float db) {

		Thread snd = new Thread(new SoundThread(this, db));
		snd.setDaemon(true);
		snd.start();
	}


}
