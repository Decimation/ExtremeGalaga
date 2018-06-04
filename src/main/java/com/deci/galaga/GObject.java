package com.deci.galaga;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import processing.core.PImage;

/**
 * Abstract game object
 *
 * @author 795835
 */
abstract class GObject {


	@Getter(AccessLevel.PACKAGE)
	private final    String ID;
	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private volatile float  x, y;
	@Getter(AccessLevel.PACKAGE)
	@Setter(AccessLevel.PACKAGE)
	private float health;

	@Getter(AccessLevel.PACKAGE)
	private boolean isAlive;

	private Resource image;
	private Resource sound;

	private AudioResource hitsound;

	private boolean eligibleForDeletion;

	GObject(final ImageResource img) {
		this();
		img.alphatize();
		this.image = img;
		this.isAlive = true;
		hitsound = Assets.getSound("hitmarker.wav");
		hitsound.setVolume(-15f);
		eligibleForDeletion = false;
	}

	private GObject() {
		ID = ShortUUID.next();
	}

	final void damage(float dmg) {
		float before = getHealth();
		setHealth(getHealth() - dmg);
		final float delta = before - getHealth();
		if (health <= 0) {
			this.destroy();
		}
		//Common.printf("%f -> %f (-%f)", before, getHealth(), delta);
		hitsound.play();
	}

	final boolean flaggedForDeletion() {
		return eligibleForDeletion;
	}

	final void flagForDeletion() {
		if (eligibleForDeletion) {
			// Already flagged
			return;
		}
		eligibleForDeletion = true;
		Common.printf(Debug.HYPERVISOR, "Flagged object %s for deletion", this.toString());
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

	/**
	 * Implementation should update the object's state when it is called.
	 */
	abstract void update() throws InterruptedException;

	/**
	 * Implementation should draw the object.
	 */
	abstract void manifest();

	/**
	 * Frees up resources allocated by a GObject instance.
	 * This should also draw the object being visually destroyed if necessary.
	 * This should only be called by the Hypervisor.
	 */
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
		return String.format("ID: %s @ %s with %f HP (%s)", ID.toString(), new Point(x, y), health, isAlive ? "alive" : "dead");
	}

}
