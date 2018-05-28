package com.deci.galaga;


import javafx.util.Pair;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class GalagaEngine extends PApplet {

	private static final String        GAME_TITLE = "Extreme Galaga";
	private static final int           FPS        = 60;
	private static final int           WIDTH      = 800;
	private static final int           HEIGHT     = 800;

	static final List<GObject> aliens     = new ArrayList<>();

	static PApplet instance;

	static GObject ship;

	static boolean canShoot;
	static int     canShootCounter;
	static int     shootingFrequency = 3;

	static {

	}

	/**
	 * Determines if an alien exists at Point p
	 *
	 * @param p Point to check for an alien object.
	 * @return A Pair containing true and the alien found at the point. A Pair
	 * containing false and null otherwise.
	 */
	static Pair<Boolean, GObject> alienAtPoint(final Point p) {

		for (final GObject g : aliens) {

			if (g.getPoint().equalsInt(p)) {
				System.out.printf("Alien with UUID %s hit at point %s\n", g.getID(), g.getPoint());
				return new Pair<>(true, g);
			}

		}
		return new Pair<>(false, null);
	}

	@Override
	public void settings() {
		size(WIDTH, HEIGHT);

	}

	@Override
	public void setup() {
		instance = this;

		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "galaga_bullet.png"));
		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "galaga.png"));
		Assets.add(new ImageResource(Assets.GITHUB_ASSETS_IMAGES, "enemy1.png"));
		Assets.add(new AudioResource("C:\\Users\\Viper\\Desktop\\Audio resources\\Game audio resources\\Source engine sounds\\", "energy_disintegrate4.wav"));
		Assets.add(new AudioResource("C:\\Users\\Viper\\Desktop\\Audio resources\\Game audio resources\\Source engine sounds\\", "fire1.wav"));
		Assets.add(new AudioResource(Assets.GITHUB_ASSETS_SOUNDS, "enemy1death.wav"));
		Assets.add(new AudioResource(Assets.GITHUB_ASSETS_SOUNDS, "pewpew.wav"));

		ship = new Ship();
		surface.setTitle(GAME_TITLE);
		smooth();
		frameRate(FPS);


		aliens.add(new Alien()); //tmp


	}



	@Override
	public void keyTyped() {
		switch (key) {
			case ' ':
				//todo
				//((Ship) ship).fire();
				break;
		}
	}


	@Override
	public void draw() {
		background(0);

		ship.update();
		ship.manifest();
		Hitbox.apply(ship);

		for (int i = Common.cast(ship, Ship.class).bulletCache.size() - 1; i >= 0; i--) {
			GBullet bullet = Common.cast(ship, Ship.class).bulletCache.get(i);
			bullet.update();

			//bullet.getSound().play();
		}

		if (keyPressed) {
			Common.autoThread(() -> ship.handleKey(key));
		}

		for (final GObject g : aliens) {
			g.manifest();
			Hitbox.apply(g);
		}

	}
}
