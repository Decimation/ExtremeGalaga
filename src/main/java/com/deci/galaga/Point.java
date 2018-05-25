package com.deci.galaga;

import lombok.Getter;
import lombok.Setter;

final class Point {

	@Getter @Setter
	private float x;

	@Getter @Setter
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

	@Override
	public boolean equals(Object p) {
		if (p.getClass().equals(this.getClass())) {
			final Point tmp = (Point) p;
			return tmp.x == this.x && tmp.y == this.y;
		}
		return false;
	}
}
