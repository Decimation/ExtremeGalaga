package com.deci.galaga;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.CountDownLatch;

class AudioResource extends Resource {


	private int plays;

	AudioResource(String path, String fileName) {
		super(path, fileName);

	}

	synchronized void play(float db) {
		new Thread(() -> {
			CountDownLatch syncLatch = new CountDownLatch(1);

			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(
						new File(this.getFullPath()));

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
