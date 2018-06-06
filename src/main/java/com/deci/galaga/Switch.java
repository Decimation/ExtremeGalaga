package com.deci.galaga;

class Switch {
	private final int unlock;
	private       int progress;

	Switch(int unlock) {
		this.unlock = unlock;
	}

	boolean poll() {
		return progress++ == unlock;
	}

	void reset() {
		progress = 0;
	}

}
