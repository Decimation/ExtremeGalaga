package com.deci.galaga;

class MathOps {
	private MathOps() {
	}

	static int getInt(int min, int max) {
		return (int) (Math.random() * (max - min)) + min;
	}

	static float getFloat(float min, float max) {
		return (float) (Math.random() * (max - min)) + min;
	}
}
