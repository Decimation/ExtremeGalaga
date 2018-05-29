package com.deci.galaga;

/**
 * Bullet entity for Galaga ship
 *
 * @author 795835
 */
class GBullet extends GObject {

	private static final int   X_ORIGIN_OFFSET = 14;
	private              float speed;
	private              float damage;
	private              float rotation;
	private              Point oldPoint;

	GBullet(final Point origin) {
		super(Assets.getImage("galaga_bullet.png"));
		setX(origin.getX());
		setY(origin.getY());
		oldPoint = new Point(GalagaEngine.instance.mouseX, GalagaEngine.instance.mouseY);
		rotation = GalagaEngine.atan2(oldPoint.getY() - getY(), oldPoint.getX() - getX()) / GalagaEngine.PI * 180;
		speed = 10;
		super.setSound(Assets.getSound("fire1.wav"));
	}

	private void checkHit() {
		for (GObject alien : GalagaEngine.aliens) {
			if (Hitbox.collision(alien, this)) {
				alien.destroy();
				Hitbox.drawIntersection(alien, this);
			}
		}
	}

	@Override
	void manifest() {
		this.setX(this.getX() + X_ORIGIN_OFFSET);
		GalagaEngine.instance.image(this.getGameImage(), this.getX(), this.getY());
		checkHit();
		Hitbox.apply(this);
		this.setX(this.getX() - X_ORIGIN_OFFSET);
	}

	@Override
	void update() {
		setX(getX() + GalagaEngine.cos(rotation / 180 * GalagaEngine.PI) * speed);
		setY(getY() + GalagaEngine.sin(rotation / 180 * GalagaEngine.PI) * speed);
		this.manifest();
		if (getX() == 0) {
			Common.cast(GalagaEngine.ship, Ship.class).bulletCache.remove(this);
		}
	}

	@Override
	void move(MovementTypes mt) {

	}

	@Override
	void handleKey(final char c) {

	}

	@Override
	void destroy() {
		Common.printf("Intersection detected @ %s", this.getPoint());

	}

	@Override
	public String toString() {
		return super.toString();
	}
}
