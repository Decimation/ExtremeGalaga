package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * Abstract game object
 *
 * @author 795835
 */
abstract class GObject {


	@Getter(AccessLevel.PACKAGE)
	private final    String  ID;
	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private volatile float x, y;
	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private float health;

	@Getter(AccessLevel.PACKAGE)
	private boolean isAlive;

	private Resource image;
	private Resource sound;

	private AudioResource hitsound;

	GObject(final ImageResource img) {
		this();
		img.alphatize();
		this.image = img;
		this.isAlive = true;
		hitsound = Assets.getSound("hitsound (2).wav");
		hitsound.setVolume(-15f);
	}

	final void damage(float dmg) {
		float before = getHealth();
		setHealth(getHealth() - dmg);
		final float delta = before - getHealth();
		if (health <= 0) {
			destroy();
		}
		Common.printf("%f -> %f (-%f)", before, getHealth(), delta);
		//DamageNumber.draw(this, delta);
		hitsound.play();

	}

	private GObject() {
		ID = ShortUUID.next();
	}

	final void rotate(double degrees) {
		image = ImageResource.rotate(getImageResource(), degrees);
	}

	final ImageResource getImageResource() {
		return (ImageResource) image;
	}

	final PImage getGameImage() {
		return ((ImageResource) image).getImage();
	}

	final AudioResource getSound() {
		return (AudioResource) sound;
	}

	final void setSound(AudioResource sound) {
		this.sound = sound;
	}

	abstract void update();

	abstract void manifest();

	void destroy() {
		isAlive = false;

	}

	abstract void handleKey(final char c);

	final void manifestInternal(final GObject g) {
		GalagaEngine.instance.image(g.getGameImage(), g.getX(), g.getY());
	}


	final Point getPoint() {
		return new Point(x, y);
	}

	@Override
	public String toString() {
		return String.format("ID: %s @ %s with %f HP", ID.toString(), new Point(x, y), health);
	}

}
