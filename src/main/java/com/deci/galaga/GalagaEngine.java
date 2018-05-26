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
	private static final List<GObject> aliens     = new ArrayList<>();
	static               PApplet       instance;
	private static       GObject       ship;

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

	private static void thread(final Runnable target) {
		new Thread(target).start();
	}

	@Override
	public void setup() {
		instance = this;

		ship = new Ship();
		frame.setTitle(GAME_TITLE);
		smooth();
		frameRate(FPS);
		size(WIDTH, HEIGHT);

		aliens.add(new Alien()); //tmp
		System.out.printf("%s\n", aliens.get(0));
	}

	private void drawBullet() {
		final EBullet e = new EBullet(ship.getPoint());
		float newY = e.getY();

		while (newY >= 0.0f) {
			newY -= 0.1f;
			e.setY(newY);
			thread(() -> image(e.getGameImg(), e.getX() + 14, e.getY()));
		}
	}

	@Override
	public void keyTyped() {
		if (key == ' ') {
			thread(this::drawBullet);
		}
	}

	@Override
	public void draw() {
		background(0);


		if (keyPressed) {
			thread(() -> ship.move(key));
		}

		image(ship.getGameImg(), ship.getX(), ship.getY());


		for (final GObject g : aliens) {
			g.draw();
		}
	}
}
