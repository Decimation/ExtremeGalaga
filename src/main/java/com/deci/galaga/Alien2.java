package com.deci.galaga;

import java.util.Random;

class Alien2 extends GObject implements IEnemy {
	private final SequentialImage si;
	private boolean invert;
	private float delta;


	Alien2() {
		super(Assets.getImage("enemy1.png"));
		super.setY(200);
		super.setSound(Assets.getSound("energy_disintegrate4.wav"));
		super.setHealth(10f);
		si = SequentialImage.create(Assets.EG_GITHUB_ASSETS_ROOT, "explosion_f2.png", "explosion_f3.png", "explosion_f4.png");
		do {
			super.setX(new Random().nextInt(GalagaEngine.HEIGHT - 25));
		} while (Hitbox.collidesWithAliens(this));
		delta = 3f;
	}

	@Override
	void update() {
		delta = Math.abs(delta);
		if (getX() >= 750) {
			invert = true;
		}

		if (getX() <= 0) {
			invert = false;
		}
		if (invert) {
			delta *= -1;

		}
		if (!invert) {
			delta = Math.abs(delta);
		}


		setX(getX() + delta);
	}

	@Override
	void manifest() {
		if (isAlive())
			super.manifestInternal(this);
	}

	@Override
	void handleKey(final char c) {

	}

	@Override
	public void explode() {
		si.advanceEvery(this.getPoint(), 5);
	}

	@Override
	void destroy() {
		if (isAlive()) {
			getSound().play();

			//explode();

			super.destroy();
		}
	}
}
