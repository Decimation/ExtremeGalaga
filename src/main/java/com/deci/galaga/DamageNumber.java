package com.deci.galaga;

class DamageNumber {

	static void draw(final GObject g, final float dmg) {
		GalagaEngine.instance.color(255, 0, 0);
		GalagaEngine.instance.text(dmg, g.getX(), g.getY() + 10);
	}
}
