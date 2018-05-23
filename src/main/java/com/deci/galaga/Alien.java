package com.deci.galaga;

class Alien extends GObject {
	private static final String IMG_URL = "https://raw.githubusercontent.com/jsvana/galaga/master/assets/images/enemy1.png";

	Alien() {
		super(IMG_URL);
		super.setY(100);
		super.setX(10);
	}

	@Override
	void draw() {
		GalagaEngine.instance.image(this.getGameImg(), this.getX(), this.getY());
	}

	@Override
	void move(MovementTypes mt) {

	}
}
