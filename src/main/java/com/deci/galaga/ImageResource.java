package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import processing.core.PImage;

class ImageResource extends Resource {

	@Getter(AccessLevel.PACKAGE)
	private PImage image;

	ImageResource(String path, String fileName) {
		super(path, fileName);
		try {
			image = GalagaEngine.instance.loadImage(super.getFullPath(), "png");
			image.format = GalagaEngine.ARGB;
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	/**
	 * Convert black pixels into transparent black pixels.
	 */
	void alphatize() {
		for (int i = 0; i < image.pixels.length; i++) {
			if (image.pixels[i] == GalagaEngine.instance.color(0, 0, 0)) {
				image.pixels[i] = GalagaEngine.instance.color(0, 0, 0, 0);
			}
		}
		image.updatePixels();
	}


}
