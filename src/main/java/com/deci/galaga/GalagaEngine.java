package com.deci.galaga;

import processing.core.PApplet;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GalagaEngine extends PApplet {

	/**
	 * A CopyOnWriteArrayList is required for concurrent modification
	 */
	static final         List<GAlien> aliens            = new CopyOnWriteArrayList<>();
	static final         int          WIDTH             = 800;
	static final         int          HEIGHT            = 800;
	private static final String       GAME_TITLE        = "Extreme Galaga";
	private static final int          FPS               = 60;
	static               PApplet      instance;
	static               boolean      canShoot;
	static               int          canShootCounter;
	static               int          shootingFrequency = 5;
	private static       GObject      ship;

	static {

	}

	static Ship getShip() {
		return (Ship) ship;
	}

	@Override
	public void settings() {
		size(WIDTH, HEIGHT);
		Hitbox.enable();
		//Hitbox.disable();
		Common.disableLog(Debug.SOUND);

	}

	@Override
	public void setup() {
		instance = this;

		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "bullet.png"));
		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "galaga_bullet.png"));
		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "galaga.png"));
		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "enemy1.png"));
		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "enemy2.png"));
		Assets.add(new AudioResource(Assets.EG_GITHUB_ASSETS_SOUND, "energy_disintegrate4.wav"));
		Assets.add(new AudioResource(Assets.GITHUB_ASSETS_SOUNDS, "enemy1death.wav"));
		Assets.add(new AudioResource(Assets.EG_GITHUB_ASSETS_SOUND, "smg1_fire1.wav"));
		Assets.add(new AudioResource(Assets.EG_GITHUB_ASSETS_SOUND, "hitmarker.wav"));
		Assets.add(new AudioResource(Assets.GITHUB_ASSETS_SOUNDS, "explosion.wav"));
		ship = new Ship();
		surface.setTitle(GAME_TITLE);

		smooth();
		frameRate(FPS);

		Hypervisor.init();
		for (int i = 0; i < 10; i++) {
			aliens.add(new StaticAlien()); //tmp
			aliens.add(new DynamicAlien());
		}


	}

	@Override
	public void draw() {
		background(0);

		ship.update();
		ship.manifest();
		Hitbox.apply(ship);

		for (int i = getShip().bulletCache.size() - 1; i >= 0; i--) {
			ShipBullet bullet = getShip().bulletCache.get(i);
			bullet.update();
		}
		//instance.rect(0, 700, Common.cast(ship, Ship.class).bulletCache.size(), 20);

		if (keyPressed) {
			Common.autoThread(() -> ship.handleKey(key));
		}

		for (final GAlien g : aliens) {
			g.update();
			g.manifest();
			Hitbox.apply(g);
			g.inline();
			g.attack();
			if (!g.isAlive()) {
				g.explode();
				//g.flagForDeletion();
			}

			/*for (int i = g.getBulletCache().size() - 1; i >= 0; i--) {
				AlienBullet bullet = g.getBulletCache().get(i);
				bullet.update();
			}*/

		}

	}
}
