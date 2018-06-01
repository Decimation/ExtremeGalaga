package com.deci.galaga;

/**
 * Bullet entity for Galaga ship
 *
 * @author 795835
 */
class GBullet extends GObject {

	private static final int     X_ORIGIN_OFFSET = 14;
	private final        float   speed;
	private final        float   rotation;
	private              float   damage;
	private              boolean hasFired;

	GBullet(final Point origin) {
		super(Assets.getImage("galaga_bullet.png"));
		setX(origin.getX());
		setY(origin.getY());
		final Point oldPoint = new Point(GalagaEngine.instance.mouseX, GalagaEngine.instance.mouseY);
		rotation = GalagaEngine.atan2(oldPoint.getY() - getY(), oldPoint.getX() - getX()) / GalagaEngine.PI * 180;
		super.rotate(rotation - 90);
		speed = 10f;
		damage = 5f;
		hasFired = false;
		super.setSound(Assets.getSound("smg1_fire1.wav"));
		//Common.printf(toString());
	}

	private void hitScan() {
		for (GObject alien : GalagaEngine.aliens) {
			if (alien.isAlive() && Hitbox.collision(alien, this)) {
				alien.damage(damage);
				Hitbox.drawIntersection(alien, this);
				this.flagForDeletion();
			}
		}
	}

	@Override
	void manifest() {
		if (isAlive()) {
			this.setX(this.getX() + X_ORIGIN_OFFSET);
			GalagaEngine.instance.image(this.getGameImage(), this.getX(), this.getY());
			hitScan();
			Hitbox.apply(this);
			this.setX(this.getX() - X_ORIGIN_OFFSET);
		}

	}

	@Override
	void update() {
		if (!hasFired) {
			getSound().play();
			hasFired = true;
		}
		setX(getX() + GalagaEngine.cos(rotation / 180 * GalagaEngine.PI) * speed);
		setY(getY() + GalagaEngine.sin(rotation / 180 * GalagaEngine.PI) * speed);
		this.manifest();

		if (getX() <= 0f || getY() <= 0f || getY() >= GalagaEngine.WIDTH || getX() >= GalagaEngine.WIDTH) {
			this.flagForDeletion();
		}
	}

	@Override
	void handleKey(final char c) {

	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	void destroy() {
		super.destroy();
	}
}
