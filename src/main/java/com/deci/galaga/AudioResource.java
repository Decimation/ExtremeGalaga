package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class AudioResource extends Resource {


	private static boolean tryToInterruptSound = false;
	private static long    mainTimeOut         = 3000;
	private static long    startTime           = System.currentTimeMillis();
	private long length;
	private AudioInputStream stream;
	private ResourceType type;
	@Setter(AccessLevel.PACKAGE)
	@Getter(AccessLevel.PACKAGE)
	private float volume;

	AudioResource(String path, String fileName) {
		this(path, fileName, ResourceType.URL);
	}

	AudioResource(String path, String fileName, ResourceType type) {
		super(path, fileName);
		this.type = type;
		volume = -10f;
	}

	private void init() {
		switch (type) {
			case FILE:
				try {
					File f = new File(this.getFullPath());
					stream = AudioSystem.getAudioInputStream(
							f);
					length = f.length();
				} catch (Exception x) {
					x.printStackTrace();
				}

				break;
			case URL:
				try {
					URL u = new URL(this.getFullPath());
					stream = AudioSystem.getAudioInputStream(
							u);
					length = Common.getFileSize(u);
				} catch (Exception x) {
					x.printStackTrace();
				}

				break;
		}
	}



	synchronized void play(float db) {
		init();
		Thread soundThread = new Thread(() -> {
			try {
				Clip clip = AudioSystem.getClip();

				AudioFormat format = stream.getFormat();

				int frameSize = format.getFrameSize();
				float frameRate = format.getFrameRate();
				long durationInMilliSeconds =
						(long) (((float) length / (frameSize * frameRate)) * 1000);

				clip.open(stream);

				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

				//float range = gainControl.getMaximum() - gainControl.getMinimum();
				//float gain = (range * db) + gainControl.getMinimum();
				gainControl.setValue(db);

				clip.setFramePosition(0);
				clip.start();
				Common.printf(Debug.SOUND, "%d: sound started", System.currentTimeMillis() - startTime);
				Thread.sleep(durationInMilliSeconds);
				while (true) {
					if (!clip.isActive()) {
						Common.printf(Debug.SOUND,"%d: sound completed", System.currentTimeMillis() - startTime);
						break;
					}
					long fPos = (long) (clip.getMicrosecondPosition() / 1000);
					long left = durationInMilliSeconds - fPos;
					Common.printf(Debug.SOUND, "%d: time left: %d", System.currentTimeMillis() - startTime, left);
					if (left > 0) Thread.sleep(left);
				}
				clip.stop();
				Common.printf(Debug.SOUND,"%d: sound stopped", System.currentTimeMillis() - startTime);
				clip.drain();
				clip.close();
				stream.close();

			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				Common.printf(Debug.SOUND, "%d: sound interrupted", System.currentTimeMillis() - startTime);
			}
		});
		soundThread.setDaemon(true);
		soundThread.start();
	}

	synchronized void play() {
		play(volume);
	}
}
