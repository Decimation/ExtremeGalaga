package com.deci.galaga;

import lombok.Getter;
import processing.core.PImage;

/**
 * Bullet entity
 *
 * @author 795835
 */
class EBullet extends GObject {

	private float speed;
	private float damage;

	@Getter
	private Point origin;
	private Point destination;
	private static final String imgUrl = "https://raw.githubusercontent.com/jsvana/galaga/master/assets/images/bullet.png";
	private static PImage gameImg;

	EBullet(Point origin) {
		this.origin = origin;
		try {
			gameImg = GalagaEngine.instance.loadImage(imgUrl, "png");
			System.out.println("[debug] Game asset loaded");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void fire() {

		while (!(GalagaEngine.alienAtPoint(origin).getKey())) {
			origin.setY(GalagaEngine.lerp(origin.getY(), 0, -0.1f));

		}
	}

	@Override
	void draw() {
		GalagaEngine.instance.image(gameImg, origin.getX(), origin.getY());
	}

	@Override
	void move(MovementTypes mt) {

	}
}
