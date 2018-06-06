package com.deci.galaga;

/**
 * Bullet entity for Galaga ship
 *
 * @author 795835
 */
class ShipBullet extends GBullet {


	ShipBullet() {
		super(GalagaEngine.getShip().getPoint());
		super.setXOriginOffset(14);

		final Point oldPoint = new Point(GalagaEngine.instance.mouseX, GalagaEngine.instance.mouseY);
		setRotation(GalagaEngine.atan2(oldPoint.getY() - getY(), oldPoint.getX() - getX()) / GalagaEngine.PI * 180);
		super.rotate(getRotation() - 90);
	}


	@Override
	void hitScan() {
		for (GAlien alien : GalagaEngine.aliens) {
			if (alien.isAlive() && Hitbox.intersection(alien, this)) {
				alien.damage(getDamage());
				Hitbox.drawIntersection(alien, this);
				super.flagForDeletion();
			}
		}
	}

	@Override
	public String toString() {
		return String.format("ShipBullet %s", super.toString());
	}
}
