package com.deci.galaga;

class Alien extends GObject {

	private boolean isAlive;

	Alien() {
		super(Assets.getImage("enemy1.png"));
		super.setY(100);
		super.setX(10);
		super.setSound(Assets.getSound("energy_disintegrate4.wav"));
		isAlive = true;
	}

	boolean isAlive() {
		return isAlive;
	}

	@Override
	void update() {

	}

	@Override
	void manifest() {
		if (isAlive)
			super.manifestInternal(this);
	}

	@Override
	void move(MovementTypes mt) {

	}

	@Override
	void handleKey(final char c) {

	}

	void explode() {
		SequentialImage.create(Assets.EG_GITHUB_ASSETS_ROOT, "explosion_f2.png", "explosion_f3.png", "explosion_f4.png").animate(getPoint());
	}

	@Override
	void destroy() {
		if (isAlive) {
			getSound().play();
			isAlive = false;


		}


	}
}
