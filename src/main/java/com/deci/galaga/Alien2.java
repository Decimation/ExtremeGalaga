package com.deci.galaga;

import java.util.Random;
class Alien2 extends GObject {
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
		int z= new Random().nextInt(2);
		if (z==1){
		setX(getX() + 1);
		setY(getY() +1);}
		if(z==2){
			setX(getX() -1);
			setY(getY() +1);
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
//test
