package com.deci.galaga;


import javafx.util.Pair;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class GalagaEngine extends PApplet {

	private static final String  GAME_TITLE = "Extreme Galaga";
	private static final int     FPS        = 60;
	private static final int     WIDTH      = 800;
	private static final int     HEIGHT     = 800;
	static               PApplet instance;

	private static final List<GObject> aliens = new ArrayList<>();

	private static final List<EBullet> bulletCache = new ArrayList<>();

	private static GObject ship;

	@Override
	public void setup() {
		instance = this;
		ship = new Ship();
		instance.frame.setTitle(GAME_TITLE);
		smooth();
		frameRate(FPS);
		size(WIDTH, HEIGHT);

		aliens.add(new Alien()); //tmp
	}

	/**
	 * Determines if an alien exists at Point p
	 * @param p Point to check for an alien object.
	 * @return A Pair containing true and the alien found at the point. A Pair
	 * containing false and null otherwise.
	 */
	static Pair<Boolean, GObject> alienAtPoint(final Point p) {
		for (final GObject g : aliens) {
			if (g.getPoint().equals(p)) return new Pair<>(true, g);
		}
		return new Pair<>(false, null);
	}

	@Override
	public void draw() {
		background(0);

		if (keyPressed) {
			if (instance.key == ' ') {
				bulletCache.add(new EBullet(ship.getPoint()));
			}
			else new Thread(() -> ship.move(instance.key)).start();
		}


		ship.draw();

		for (EBullet e : bulletCache) {
			final Thread bullet = new Thread(e::fire);
			bullet.start();
			try {
				bullet.join();
			} catch (Exception x) {}
			new Thread(() -> {
				while (!alienAtPoint(e.getOrigin()).getKey()) {
					e.draw();
				}
			}).start();

		}
		for (GObject g : aliens) {
			g.draw();
		}
	}
}
