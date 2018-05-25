package com.deci.galaga;


final class Ship extends GObject {

	private static final String IMG_URL    = "https://raw.githubusercontent.com/jsvana/galaga/master/assets/images/galaga.png";
	private static final float  LERP_DELTA = 0.1f;

	/**
	 * How many x pixels to move by when changing coordinates
	 */
	private float xDelta;

	Ship() {
		super(IMG_URL);
		setX(0);
		setY(500);
		setHealth(100f);
		xDelta = 25;
	}

	@Override
	void draw() {
		GalagaEngine.instance.image(this.getGameImg(), this.getX(), this.getY());
	}

	@Override
	void move(MovementTypes mt) {
		switch (mt) {
			case LEFT:
				setX(GalagaEngine.lerp(getX(), getX() - xDelta, LERP_DELTA));
				break;
			case RIGHT:
				setX(GalagaEngine.lerp(getX(), getX() + xDelta, LERP_DELTA));
				break;
			default:
				break;
		}
	}


	void shoot() {

	}

}
