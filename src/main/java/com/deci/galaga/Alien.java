package com.deci.galaga;

class Alien extends GObject {

	private SequentialImage si;

	Alien() {
		super(Assets.getImage("enemy1.png"));
		super.setY(100);
		super.setX(10);
		super.setSound(Assets.getSound("enemy1death.wav"));
		super.setHealth(10f);
		si = SequentialImage.create(Assets.EG_GITHUB_ASSETS_ROOT, "explosion_f2.png", "explosion_f3.png", "explosion_f4.png");
	}


	@Override
	void update() {

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
		si.advanceEvery(this.getPoint(), 5);
		//SequentialImage.create(Assets.EG_GITHUB_ASSETS_ROOT, "explosion_f2.png", "explosion_f3.png", "explosion_f4.png").animate(getPoint());
	}

	@Override
	void destroy() {
		if (isAlive()) {
			getSound().play();
			super.destroy();


		}


	}
}
