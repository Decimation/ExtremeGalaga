package com.deci.galaga;

import lombok.Getter;
import lombok.Setter;

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

	void incrementX(float delta) {
		x += delta;
	}

	void incrementY(float delta) {
		y += delta;
	}

	boolean equalsInt(final Point p) {
		int i_pX = (int) p.getX();
		int i_pY = (int) p.getY();
		int i_X = (int) x;
		int i_Y = (int) y;

		System.out.printf("Comparing (%d, %d) to (%d, %d)\n", i_X, i_Y, i_pX, i_pY);

		return i_X == i_pX && i_Y == i_pY;
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
