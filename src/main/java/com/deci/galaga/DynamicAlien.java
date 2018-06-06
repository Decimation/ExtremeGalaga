package com.deci.galaga;

import java.util.Random;

class DynamicAlien extends GAlien {

	private final Switch  attackSwitch;
	private       boolean invert;
	private       float   delta;

	DynamicAlien() {
		super(Assets.getImage("enemy1.png"));
		super.setY(200);
		super.setSound(Assets.getSound("energy_disintegrate4.wav"));
		super.setHealth(10f);
		do {
			super.setX(new Random().nextInt(GalagaEngine.HEIGHT - 25));
		} while (Hitbox.collidesWithAliens(this));
		delta = 3f;
		attackSwitch = new Switch(10);
	}

	@Override
	void attack() {

		for (int i = getBulletCache().size() - 1; i >= 0; i--) {
			GBullet bullet = getBulletCache().get(i);
			bullet.update();

		}


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
	public String toString() {
		return String.format("DynamicAlien %s", super.toString());
	}
}
