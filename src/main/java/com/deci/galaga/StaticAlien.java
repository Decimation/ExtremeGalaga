package com.deci.galaga;

import java.util.Random;

class StaticAlien extends GAlien {

	StaticAlien() {
		super(Assets.getImage("enemy2.png"));
		super.setY(100);

		do {
			super.setX(new Random().nextInt(GalagaEngine.WIDTH - 25));
		} while (Hitbox.collidesWithAliens(this));

		//super.setSound(Assets.getSound("enemy1death.wav"));
		super.setSound(Assets.getSound("energy_disintegrate4.wav"));
		super.setHealth(10f);

		//Common.printf(toString());
	}

	@Override
	void update() {

	}

	@Override
	void attack() {
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
		return String.format("StaticAlien %s", super.toString());
	}
}
