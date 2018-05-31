package com.deci.galaga;

import javafx.util.Pair;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.sound.sampled.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class AudioResource extends Resource {


	private static boolean tryToInterruptSound = false;
	private static long    mainTimeOut         = 3000;
	private static long    startTime           = System.currentTimeMillis();
	private int length;
	//private AudioInputStream stream;
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

		/*switch (type) {
			case URL:
				try {
					stream = AudioSystem.getAudioInputStream(new URL(getFullPath()));

				} catch (Exception x) {
					x.printStackTrace();
				}
				break;
			case FILE:
				try {
					stream = AudioSystem.getAudioInputStream(new File(getFullPath()));
				} catch (Exception x) {
					x.printStackTrace();
				}
				break;


		}
		memory = serialize(stream);*/
	}

	/*private static AudioInputStream convertToPCM(AudioInputStream audioInputStream)
	{
		AudioFormat m_format = audioInputStream.getFormat();

		if ((m_format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) &&
				(m_format.getEncoding() != AudioFormat.Encoding.PCM_UNSIGNED))
		{
			AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					m_format.getSampleRate(), 16,
					m_format.getChannels(), m_format.getChannels() * 2,
					m_format.getSampleRate(), m_format.isBigEndian());
			audioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
		}

		return audioInputStream;
	}


	private static byte[] serialize(AudioInputStream in) {
		byte[] data = null;
		DataInputStream dis = new DataInputStream(in);
		try
		{
			AudioFormat format = in.getFormat();
			data = new byte[(int)(in.getFrameLength() * format.getFrameSize())];
			dis.readFully(data);
			dis.close();
		} catch (Exception x) {
			x.printStackTrace();
		}
		finally
		{

		}
		return data;
	}*/

	/*private void init() {
		final long old = System.currentTimeMillis();
		switch (type) {
			case FILE:
				try {
					File f = new File(this.getFullPath());
					stream = AudioSystem.getAudioInputStream(
							f);
					length = (int) f.length();
				} catch (Exception x) {
					x.printStackTrace();
				}

				break;
			case URL:
				try {
					URL u = new URL(this.getFullPath());

					length = Common.getFileSize(u);

					stream = AudioSystem.getAudioInputStream(u);

				} catch (Exception x) {
					x.printStackTrace();
				}

				break;
		}


		Common.printf(Debug.SOUND, "Initialization took %d ms", System.currentTimeMillis() - old);
	}*/

	private Clip clip;
	private AudioFormat format;

	synchronized void play() {
		play(-10f);
	}

	//todo: fix choppy sounds and FIGURE OUT A MORE EFFICIENT WAY TO PLAY SOUNDS FFS
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

			clip.setFramePosition(0);
			clip.start();
			Common.printf(Debug.SOUND, "%d: sound started", System.currentTimeMillis() - startTime);

			Common.sleep(durationInMilliSeconds);
			while (true) {
				if (!clip.isActive()) {
					Common.printf(Debug.SOUND,"%d: sound completed", System.currentTimeMillis() - startTime);
					break;
				}
				long fPos = (clip.getMicrosecondPosition() / 1000);
				long left = durationInMilliSeconds - fPos;
				Common.printf(Debug.SOUND, "%d: time left: %d", System.currentTimeMillis() - startTime, left);
				if (left > 0) Common.sleep(left);
			}
			clip.stop();
			Common.printf(Debug.SOUND,"%d: sound stopped", System.currentTimeMillis() - startTime);
			//clip.drain();
			//clip.close();
		});
		snd.setDaemon(true);
		snd.start();
	}

	// I HOPE ORACLE BURNS IN HELL

	/*synchronized void playLine(AudioInputStream audioStream) {
		try {

			int BUFFER_SIZE = 128000;
			AudioFormat audioFormat = null;
			SourceDataLine sourceLine = null;

			audioFormat = audioStream.getFormat();

			sourceLine = AudioSystem.getSourceDataLine(audioFormat);
			sourceLine.open(audioFormat);
			sourceLine.start();

			int nBytesRead = 0;
			byte[] abData = new byte[BUFFER_SIZE];
			while (nBytesRead != -1) {
				try {
					nBytesRead =
							audioStream.read(abData, 0, abData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (nBytesRead >= 0) {
					int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
				}
			}

			sourceLine.drain();
			sourceLine.close();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	synchronized void play() {
		playLine(stream);
	}

	synchronized void play2(float db) {
		//init();
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
					long fPos = (clip.getMicrosecondPosition() / 1000);
					long left = durationInMilliSeconds - fPos;
					Common.printf(Debug.SOUND, "%d: time left: %d", System.currentTimeMillis() - startTime, left);
					if (left > 0) Thread.sleep(left);
				}
				clip.stop();
				Common.printf(Debug.SOUND,"%d: sound stopped", System.currentTimeMillis() - startTime);
				clip.drain();
				clip.close();

				//stream.close();

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

	synchronized void play2() {
		play2(volume);
	}*/
}
