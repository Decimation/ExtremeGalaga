package com.deci.galaga;

import com.deci.galaga.GalagaEngine;
import processing.core.PImage;

/**
 * Abstract game object
 *
 * @author 795835
 */
abstract class GObject {

	private PImage gameImg;
	private volatile float    x, y;



	GObject(final String imgUrl) {
		this();
		try {
			gameImg = GalagaEngine.instance.loadImage(imgUrl, "png");
			System.out.println("[debug] Game asset loaded");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	GObject() {
		System.out.println("[debug] GObject initialized");
	}


	abstract void draw();
	abstract void move(MovementTypes mt);



	PImage getGameImg() {
		return gameImg;
	}

	final float getY() {
		return y;
	}

	void setY(float y) {
		this.y = y;
	}

	final float getX() {
		return x;
	}

	void setX(float x) {
		//this.x = JGalagaEngine.lerp(this.x,x,LERP_DELTA);

		this.x = x;
	}
}
