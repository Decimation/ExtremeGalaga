package com.deci.galaga;


final class Ship extends GObject {

	private static final String IMG_URL    = "https://raw.githubusercontent.com/jsvana/galaga/master/assets/images/galaga.png";
	private static final float  LERP_DELTA = 0.1f;
	/**
	 * How many x pixels to move by when changing coordinates
	 */
	private              float  xDelta;

	Ship() {
		super(IMG_URL);
		super.setY(0);
		super.setX(0);
		xDelta = 10;

	}

	@Override
	void draw() {
		GalagaEngine.instance.image(this.getGameImg(), this.getX(), this.getY());
	}

	@Override
	void move(MovementTypes mt) {

		switch (mt) {
			case LEFT:
				//this.setX(this.getX() - xDelta);
				lerpX(getX() - xDelta, LERP_DELTA);
				break;
			case RIGHT:
				//this.setX(this.getX() + xDelta);
				lerpX(getX() + xDelta, LERP_DELTA);
				break;

			default:
				break;
		}
	}


	// todo
	private final void lerpX(float to, float delta) {
		float oldX = getX();
		System.out.printf("\nOld X: %f | To: %f | Delta: %f\n", oldX, to, delta);

		if (oldX < to) {
			for (float f = oldX; f < to; f += delta) {
				setX(f);
				draw();
			}
		} else {
			for (float f = oldX; f > to; f -= delta) {
				setX(f);
				draw();
			}
		}


	}


	void shoot() {

	}

}
