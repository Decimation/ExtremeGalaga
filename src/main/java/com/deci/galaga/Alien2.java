package com.deci.galaga;

public class Alien2 extends GObject {
	private final SequentialImage si;


	Alien2() {
		super(Assets.getImage("enemy1.png"));
		super.setY(200);
		super.setX(10);
		//super.setSound(Assets.getSound("enemy1death.wav"));
		super.setSound(Assets.getSound("energy_disintegrate4.wav"));
		super.setHealth(10f);
		si = SequentialImage.create(Assets.EG_GITHUB_ASSETS_ROOT, "explosion_f2.png", "explosion_f3.png", "explosion_f4.png");

	}


	@Override
	void update() {
		while (getX() < 800 && getX() > 0) {
			while (getY() > getX() - 5 || getX() < getX() + 5) {
				while (getY() > getX() - 5) {
					setY(getY() - 1);
				}

				while (getY() < getX() + 5) {
					setY(getY() + 1);

				}
			}
		}


	}

	@Override
	void manifest() {
		if (isAlive())
			super.manifestInternal(this);
	}


	@Override
	void handleKey(final char c) {

	}

	void explode() {
		SequentialImage.create(Assets.EG_GITHUB_ASSETS_ROOT, "explosion_f2.png", "explosion_f3.png", "explosion_f4.png").animate(getPoint());
	}

	@Override
	void destroy() {
		if (isAlive()) {
			getSound().play();

			GalagaEngine.aliens.remove(this);
			//explode();
			super.destroy();
		}


	}
}
//test
