package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

import java.util.UUID;

/**
 * Abstract game object
 *
 * @author 795835
 */
abstract class GObject {


	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private volatile float x, y;

	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private float health;

	@Getter(AccessLevel.PACKAGE)
	private final UUID ID;

	private Resource image;
	private Resource sound;

	GObject(final ImageResource img) {
		this();
		img.alphatize();
		this.image = img;
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

	private GObject() {
		ID = UUID.randomUUID();
	}

	abstract void update();

	abstract void manifest();

	abstract void move(final MovementTypes mt);

	abstract void destroy();

	abstract void handleKey(final char c);

	final void manifestInternal(final GObject g) {
		GalagaEngine.instance.image(g.getGameImage(), g.getX(), g.getY());
	}

	final void move(char k) {
		if (k == 'a') {
			move(MovementTypes.LEFT);
		} else if (k == 'd') {
			move(MovementTypes.RIGHT);
		}
	}



	final Point getPoint() {
		return new Point(x, y);
	}

	@Override
	public String toString() {
		return String.format("ID: %s @ %s with %f HP", ID.toString(), new Point(x, y), health);
	}

}
