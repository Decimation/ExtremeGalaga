package com.deci.galaga;

/**
 * Bullet entity
 *
 * @author 795835
 */
class EBullet extends GObject {

	private static final int    X_ORIGIN_OFFSET = 14;
	private              float  speed;
	private              float  damage;
	private              float  rotation;
	private              Point  oldPoint;

	EBullet(final Point origin) {
		super(Assets.getImage("bullet.png"));
		setX(origin.getX());
		setY(origin.getY());
		oldPoint = new Point(GalagaEngine.instance.mouseX, GalagaEngine.instance.mouseY);
		rotation = GalagaEngine.atan2(oldPoint.getY() - getY(), oldPoint.getX() - getX()) / GalagaEngine.PI * 180;
		speed = 10;
		super.setSound(Assets.getSound("fire1.wav"));
	}

	@Override
	void manifest() {
		GalagaEngine.instance.image(this.getGameImage(), this.getX() + X_ORIGIN_OFFSET, this.getY());


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

	}

	@Override
	public String toString() {
		return super.toString();
	}
}
