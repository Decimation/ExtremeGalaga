package com.deci.galaga;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class Ship extends GObject implements IMoveable {

	private static final float LERP_DELTA = 0.1f;
	List<ShipBullet> bulletCache;

	/**
	 * How many x pixels to move by when changing coordinates
	 */
	private float xDelta;

	Ship() {
		super(Assets.getImage("galaga.png"));
		setX(0f);
		setY(500f);
		setHealth(100f);
		xDelta = 40f;
		bulletCache = new CopyOnWriteArrayList<>();
		super.setSound(Assets.getSound("explosion.wav"));
	}


	@Override
	void update() {
		if (GalagaEngine.instance.mousePressed && GalagaEngine.canShoot) {
			bulletCache.add(new ShipBullet());
			GalagaEngine.canShoot = false;
			GalagaEngine.canShootCounter = 0;
		} else {
			GalagaEngine.canShootCounter++;
			if (GalagaEngine.canShootCounter == GalagaEngine.shootingFrequency) {
				GalagaEngine.canShoot = true;
			}
		}
	}

	@Override
	void manifest() {
		super.manifestInternal(this);
	}

	@Override
	public void move(MovementTypes mt) {
		switch (mt) {
			case LEFT:
				setX(GalagaEngine.lerp(getX(), getX() - xDelta, LERP_DELTA));
				break;
			case RIGHT:
				setX(GalagaEngine.lerp(getX(), getX() + xDelta, LERP_DELTA));
				break;
			default:
				break;
		}
	}

	@Override
	public void move(char c) {
		if (c == 'a') {
			move(MovementTypes.LEFT);
		} else if (c == 'd') {
			move(MovementTypes.RIGHT);
		}
	}


	@Override
	void handleKey(final char c) {
		switch (c) {
			default:
				move(c);
		}
	}

	@Override
	void damage(float dmg) {
		super.damageInternal(dmg);
		if (getHealth() <= 0) {
			this.getSound().play();
			this.destroy();

			Common.sleep(1000 * 5);
			GalagaEngine.instance.exit();
		}
	}
}
