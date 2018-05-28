package com.deci.galaga;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.File;
import java.util.concurrent.CountDownLatch;

class AudioResource extends Resource {


	private int     plays;

	AudioResource(String path, String fileName) {
		super(path, fileName);

	}


	synchronized void play() {


		new Thread(() -> {
			CountDownLatch syncLatch = new CountDownLatch(1);

			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(
						new File(this.getFullPath()));

				Clip clip = AudioSystem.getClip();

				// Listener which allow method return once sound is completed
				clip.addLineListener(e -> {
					if (e.getType() == LineEvent.Type.STOP) {
						syncLatch.countDown();
					}
				});

				clip.open(stream);
				clip.start();


				syncLatch.await();
			} catch (Exception x) {
				x.printStackTrace();
			}
			plays++;
		}).start();


	}
}
