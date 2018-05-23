package com.deci.galaga;


import processing.core.PApplet;

public class GalagaEngine extends PApplet {

	private static final int FPS    = 60;
	private static final int WIDTH  = 800;
	private static final int HEIGHT = 800;
	static PApplet instance;

	static {

	}

	private GObject ship, tmp;

	@Override
	public void setup() {
		instance = this;
		ship = new Ship();
		tmp = new Alien();
		smooth();
		frameRate(FPS);
		size(WIDTH, HEIGHT);
	}

	@Override
	public void draw() {
		background(0);
		ship.draw();
		tmp.draw();
	}

	@Override
	public void keyPressed() {
		if (instance.key == 'a') {
			ship.move(MovementTypes.LEFT);
		} else if (instance.key == 'd') {
			ship.move(MovementTypes.RIGHT);
		}
	}
}
