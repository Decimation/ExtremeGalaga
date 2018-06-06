package com.deci.galaga;

class AlienBullet extends GBullet {

	AlienBullet(GAlien alien) {
		super(Assets.getImage("bullet.png"));
		super.setX(alien.getX());
		super.setY(alien.getY());

		super.setRotation(GalagaEngine.atan2(GalagaEngine.getShip().getY() - alien.getY(), GalagaEngine.getShip().getX() - alien.getX()) / GalagaEngine.PI * 180);
		rotate(getRotation() - 90);
		super.setSpeed(5f);
	}

	@Override
	void hitScan() {
		if (Hitbox.intersection(GalagaEngine.getShip(), this)) {
			GalagaEngine.getShip().damage(getDamage());
			Hitbox.drawIntersection(GalagaEngine.getShip(), this);
			super.flagForDeletion();
		}
	}

	@Override
	public String toString() {
		return String.format("AlienBullet %s", super.toString());
	}
}
