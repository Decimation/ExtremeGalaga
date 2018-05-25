package com.deci.galaga;

/**
 * Bullet entity
 *
 * @author 795835
 */
class EBullet {

	private float speed;
	private float damage;
	private Point origin;
	private Point destination;
	private static final String imgUrl = "https://raw.githubusercontent.com/jsvana/galaga/master/assets/images/bullet.png";

	void fire() {

		while (!(GalagaEngine.alienAtPoint(origin).getKey())) {

		}
	}


}
