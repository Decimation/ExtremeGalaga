package com.deci.galaga;

import com.jcabi.aspects.Async;

/**
 * Bullet entity
 *
 * @author 795835
 */
class EBullet extends GObject {

	private static final String imgUrl          = "https://raw.githubusercontent.com/jsvana/galaga/master/assets/images/bullet.png";
	private static final int    X_ORIGIN_OFFSET = 14;
	private              float  speed;
	private              float  damage;
	private              Point  destination;

	EBullet(Point origin) {
		super(imgUrl);
		setX(origin.getX());
		setY(origin.getY());
		//System.out.printf("EBullet %s origin @ %s\n", getID().toString(), origin.toString());
	}


	@Async
	@Override
	void draw() {
		GalagaEngine.instance.image(getGameImg(), this.getX() + X_ORIGIN_OFFSET, this.getY());
	}

	@Override
	void move(MovementTypes mt) {

	}
}
