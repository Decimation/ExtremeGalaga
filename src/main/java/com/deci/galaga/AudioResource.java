package com.deci.galaga;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

class AudioResource extends Resource {


	private int              plays;
	private AudioInputStream stream;

	AudioResource(String path, String fileName) {
		super(path, fileName);
		try {
			stream = AudioSystem.getAudioInputStream(
					new File(this.getFullPath()));
		} catch (Exception x) {
		}
	}

	AudioResource(String path, String fileName, ResourceType type) {
		this(path, fileName);
		switch (type) {
			case FILE:
				try {
					stream = AudioSystem.getAudioInputStream(
							new File(this.getFullPath()));
				} catch (Exception x) {
				}

				break;
			case URL:
				try {
					stream = AudioSystem.getAudioInputStream(
							new URL(this.getFullPath()));
				} catch (Exception x) {
				}

				break;
		}
	}

	synchronized void play(float db) {
		new Thread(() -> {
			CountDownLatch syncLatch = new CountDownLatch(1);

			try {

				Clip clip = AudioSystem.getClip();
				clip.open(stream);

				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

				//float range = gainControl.getMaximum() - gainControl.getMinimum();
				//float gain = (range * db) + gainControl.getMinimum();
				gainControl.setValue(db);

				// Listener which allow method return once sound is completed
				clip.addLineListener(e -> {
					if (e.getType() == LineEvent.Type.STOP) {
						syncLatch.countDown();
					}
				});

				clip.start();


				syncLatch.await();
			} catch (Exception x) {
				x.printStackTrace();
			}
			plays++;
		}).start();
	}

	synchronized void play() {
		play(-15f);
	}
}
