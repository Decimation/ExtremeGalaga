package com.deci.galaga;

class SequentialImage {

	private final Resource[] frames;

	// No bounds checking as of now

	private SequentialImage(int frames) {
		this.frames = new ImageResource[frames];
	}

	static SequentialImage create(final String root, String... fileNames) {
		SequentialImage si = new SequentialImage(fileNames.length);
		for (int i = 0; i < si.length(); i++) {
			si.frames[i] = new ImageResource(root, fileNames[i]);
			((ImageResource) si.frames[i]).alphatize();
		}
		return si;
	}

	void animate(final Point p) {
		for (int i = 0; i < frames.length; i++) {
			GalagaEngine.instance.image(((ImageResource) frames[i]).getImage(), p.getX(), p.getY());
		}
	}

	int length() {
		return frames.length;
	}

	void setFrame(int index, ImageResource frame) {
		this.frames[index] = frame;
	}

	ImageResource getFrame(int index) {
		return (ImageResource) this.frames[index];
	}


}
