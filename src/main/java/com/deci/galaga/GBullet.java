package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

abstract class GBullet extends GObject {

	@Setter(AccessLevel.PACKAGE)
	@Getter(AccessLevel.PACKAGE)
	private float speed;

	@Setter(AccessLevel.PACKAGE)
	@Getter(AccessLevel.PACKAGE)
	private int   xOriginOffset;
	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private float rotation;

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private float   damage;
	private boolean hasFired;

	GBullet(final Point origin) {
		this();
		setX(origin.getX());
		setY(origin.getY());
	}

	GBullet(final ImageResource img) {
		super(img);
		speed = 10f;
		damage = 5f;
		hasFired = false;
		super.setSound(Assets.getSound("smg1_fire1.wav"));
		super.getSound().setVolume(-30f);
	}

	GBullet() {
		this(Assets.getImage("galaga_bullet.png"));

	}

	abstract void hitScan();

	@Override
	final void manifest() {
		if (isAlive()) {
			this.setX(this.getX() + xOriginOffset);
			GalagaEngine.instance.image(this.getGameImage(), this.getX(), this.getY());
			hitScan();
			Hitbox.apply(this);
			this.setX(this.getX() - xOriginOffset);
		}

	}

	@Override
	final void update() {
		if (!hasFired) {
			getSound().play();
			hasFired = true;
		}
		setX(getX() + GalagaEngine.cos(rotation / 180 * GalagaEngine.PI) * speed);
		setY(getY() + GalagaEngine.sin(rotation / 180 * GalagaEngine.PI) * speed);
		this.manifest();

		if (getX() <= 0f || getY() <= 0f || getY() >= GalagaEngine.WIDTH || getX() >= GalagaEngine.WIDTH) {
			flagForDeletion();
		}
	}

	@Override
	final void handleKey(final char c) {

	}

	@Override
	final void destroy() {
		super.destroy();
	}

	@Override
	final void damage(float dmg) {
		super.damageInternal(dmg);
	}
}
