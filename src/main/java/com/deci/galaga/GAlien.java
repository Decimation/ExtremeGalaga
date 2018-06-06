package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class GAlien extends GObject {

	private final SequentialImage si;

	private List<GBullet> bulletCache;
	private boolean       canShoot;
	private int           canShootCounter;

	@Setter(AccessLevel.PACKAGE)
	@Getter(AccessLevel.PACKAGE)
	private int shootingFrequency;

	GAlien(final ImageResource img) {
		super(img);
		si = SequentialImage.create(Assets.EG_GITHUB_ASSETS_ROOT, "explosion_f2.png", "explosion_f3.png", "explosion_f4.png");
		bulletCache = new CopyOnWriteArrayList<>();

		shootingFrequency = MathOps.getInt(35, 55);

	}

	final List<GBullet> getBulletCache() {
		return bulletCache;
	}

	final void inline() {
		if (isAlive() && !this.flaggedForDeletion() && canShoot) {
			bulletCache.add(new AlienBullet(this));
			canShoot = false;
			canShootCounter = 0;
		} else {
			canShootCounter++;
			if (canShootCounter == shootingFrequency) {
				canShoot = true;
			}
		}
	}

	final void explode() {
		si.advanceEvery(this.getPoint(), 5);
		if (si.isComplete()) this.flagForDeletion();
	}

	final void destroy() {
		if (isAlive()) {
			getSound().play();
			super.destroy();
		}
	}

	@Override
	final void damage(float dmg) {
		super.damageInternal(dmg);
		if (getHealth() <= 0) {
			this.destroy();

		}
	}

	abstract void attack();
}
