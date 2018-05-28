package com.deci.galaga;

class Alien extends GObject {

	Alien() {
		super(Assets.getImage("enemy1.png"));
		super.setY(100);
		super.setX(10);
		super.setSound(Assets.getSound("energy_disintegrate4.wav"));
	}

	@Override
	void update() {

	}

	@Override
	void manifest() {
		super.manifestInternal(this);
	}

	@Override
	void move(MovementTypes mt) {

	}

	@Override
	void handleKey(final char c) {

	}

	@Override
	void destroy() {
		getSound().play();
	}
}
