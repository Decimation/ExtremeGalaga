package com.deci.galaga;

import processing.core.PApplet;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GalagaEngine extends PApplet {

	/**
	 * A CopyOnWriteArrayList is required for concurrent modification
	 */
	static final         List<GObject> aliens     = new CopyOnWriteArrayList<>();
	static final         int           WIDTH      = 800;
	static final         int           HEIGHT     = 800;
	private static final String        GAME_TITLE = "Extreme Galaga";
	private static final int           FPS        = 60;
	static               PApplet       instance;

	static GObject ship;

	static boolean canShoot;
	static int     canShootCounter;
	static int     shootingFrequency = 5;

	static {

	}

	@Override
	public void settings() {
		size(WIDTH, HEIGHT);
		Hitbox.disable();
		Common.disableLog(Debug.SOUND);

	}

	@Override
	public void setup() {
		instance = this;

		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "galaga_bullet.png"));
		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "galaga.png"));
		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "enemy1.png"));
		Assets.add(new AudioResource(Assets.EG_GITHUB_ASSETS_SOUND, "energy_disintegrate4.wav"));
		Assets.add(new AudioResource(Assets.GITHUB_ASSETS_SOUNDS, "enemy1death.wav"));
		Assets.add(new AudioResource(Assets.EG_GITHUB_ASSETS_SOUND, "smg1_fire1.wav"));
		Assets.add(new AudioResource(Assets.EG_GITHUB_ASSETS_SOUND, "hitmarker.wav"));
		ship = new Ship();
		surface.setTitle(GAME_TITLE);

		smooth();
		frameRate(FPS);

		for (int i = 0; i < 10; i++)
			aliens.add(new Alien()); //tmp

		aliens.add(new Alien2());

		Hypervisor.init();
	}

	static Ship getShip() {
		return (Ship) ship;
	}


	@Override
	public void draw() {
		background(0);

		ship.update();
		ship.manifest();
		Hitbox.apply(ship);

		for (int i = getShip().bulletCache.size() - 1; i >= 0; i--) {
			GBullet bullet = getShip().bulletCache.get(i);
			bullet.update();
		}
		//instance.rect(0, 700, Common.cast(ship, Ship.class).bulletCache.size(), 20);

		if (keyPressed) {
			Common.autoThread(() -> ship.handleKey(key));
		}

		for (final GObject g : aliens) {
			g.update();
			g.manifest();
			Hitbox.apply(g);
			if (!g.isAlive()) {
				((IEnemy) g).explode();
			}
		}

	}
}
