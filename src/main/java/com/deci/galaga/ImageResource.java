package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import processing.core.PImage;

import java.awt.*;
import java.awt.image.BufferedImage;

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

	private ImageResource() {
		super();
	}

	static ImageResource rotate(ImageResource ir, double degrees) {
		final Image sub = ir.getImage().getImage(); // Java in a nutshell
		Image rotated = ImgOps.rotateBy((BufferedImage) sub, degrees);
		PImage newPImg = new PImage(rotated);
		ImageResource cpy = new ImageResource();
		cpy.image = newPImg;
		return cpy;
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
