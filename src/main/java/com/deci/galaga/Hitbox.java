package com.deci.galaga;

import lombok.SneakyThrows;

import java.awt.*;

class Hitbox {

	private static boolean enabled;

	static {

	}

	static void enable() {
		enabled = true;
	}

	static void disable() {
		enabled = false;
	}

	static void apply(final GObject obj) {
		if (!enabled) {return;}
		GalagaEngine.instance.noFill();
		GalagaEngine.instance.stroke(255, 0, 0);
		GalagaEngine.instance.rect(obj.getX(), obj.getY(), obj.getGameImage().width, obj.getGameImage().height);
		GalagaEngine.instance.fill(255);
	}

	@SneakyThrows
	static void drawIntersection(final GObject a, final GObject b) {
		if (!enabled) {
			return;
		}
		if (!collision(a, b)) {
			// todo: make GalagaException class
			throw new Exception("No intersection detected");
		}
		Rectangle intersection = new Rectangle((int) a.getX(), (int) a.getY(), a.getGameImage().width, a.getGameImage().height);
		intersection = intersection.intersection(new Rectangle((int) b.getX(), (int) b.getY(), b.getGameImage().width, b.getGameImage().height));
		GalagaEngine.instance.stroke(0, 255, 0);
		GalagaEngine.instance.line(intersection.x, intersection.y, 800, 800);
		GalagaEngine.instance.line(intersection.x, intersection.y, 800, intersection.y);


	}

	static boolean collision(final GObject a, final GObject b) {
		Rectangle ar = new Rectangle((int) a.getX(), (int) a.getY(), a.getGameImage().width, a.getGameImage().height);
		return ar.intersects(new Rectangle((int) b.getX(), (int) b.getY(), b.getGameImage().width, b.getGameImage().height));
	}
}
