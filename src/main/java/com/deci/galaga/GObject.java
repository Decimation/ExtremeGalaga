package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import java.util.UUID;

/**
 * Abstract game object
 *
 * @author 795835
 */
abstract class GObject {

	@Getter(AccessLevel.PACKAGE)
	private PImage gameImg;

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private volatile float x, y;

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private float health;

	@Getter(AccessLevel.PACKAGE)
	private UUID ID;

	GObject(final String imgUrl) {
		this();

		try {
			gameImg = GalagaEngine.instance.loadImage(imgUrl, "png");
			gameImg.format = GalagaEngine.ARGB;
			//System.out.println("[debug] Game asset loaded");
		} catch (Exception e) {
			e.printStackTrace();
		}
		alphatize();

	}

	GObject() {
		ID = UUID.randomUUID();
		//System.out.printf("[debug] GObject initialized with UUID %s\n", ID.toString());
	}

	abstract void draw();

	abstract void move(MovementTypes mt);

	final void move(char k) {
		if (k == 'a') {
			move(MovementTypes.LEFT);
		} else if (k == 'd') {
			move(MovementTypes.RIGHT);
		}
	}

	/**
	 * Convert black pixels into transparent black pixels.
	 */
	private void alphatize() {
		for (int i = 0; i < gameImg.pixels.length; i++) {
			if (gameImg.pixels[i] == GalagaEngine.instance.color(0, 0, 0)) {
				gameImg.pixels[i] = GalagaEngine.instance.color(0, 0, 0, 0);
			}
		}
		gameImg.updatePixels();
	}

	final Point getPoint() {
		return new Point(x, y);
	}

	final int height() {
		return gameImg.height;
	}

	final int width() {
		return gameImg.width;
	}

	@Override
	public String toString() {
		return String.format("ID: %s @ %s with %f HP", ID.toString(), new Point(x, y), health);
	}

}
