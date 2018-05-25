package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

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

	GObject(final String imgUrl) {
		this();
		try {
			gameImg = GalagaEngine.instance.loadImage(imgUrl, "png");
			System.out.println("[debug] Game asset loaded");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	GObject() {
		System.out.println("[debug] GObject initialized");
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

	final Point getPoint() {
		return new Point(x, y);
	}
}
