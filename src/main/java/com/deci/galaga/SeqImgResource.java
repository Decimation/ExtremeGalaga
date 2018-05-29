package com.deci.galaga;

import processing.core.PImage;

class SeqImgResource extends Resource {

	private final PImage[] frames;

	SeqImgResource(String path, String fileName, int frames) {
		super(path, fileName);
		this.frames = new PImage[frames];
	}


}
