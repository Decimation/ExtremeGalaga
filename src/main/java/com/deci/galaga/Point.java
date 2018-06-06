package com.deci.galaga;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

final class Point {

	@Getter
	@Setter
	private float x;

	@Getter
	@Setter
	private float y;

	Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	private Point() {
		this.x = 0;
		this.y = 0;
	}

	static Point random(Point p) {
		Random r = new Random();
		Point np = new Point();
		if (p.x > 0)
			np.setX(r.nextInt((int) p.x));
		if (p.y > 0)
			np.setY(r.nextInt((int) p.y));
		return np;
	}

	void incrementX(float delta) {
		x += delta;
	}

	void incrementY(float delta) {
		y += delta;
	}

	@Override
	public boolean equals(Object p) {
		if (p.getClass().equals(this.getClass())) {
			final Point tmp = (Point) p;
			return tmp.x == this.x && tmp.y == this.y;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("(%f, %f)", x, y);
	}
}
