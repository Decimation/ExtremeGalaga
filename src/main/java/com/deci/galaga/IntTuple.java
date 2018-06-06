package com.deci.galaga;

class IntTuple {
	private int key;
	private int value;

	IntTuple() {
		key = 0;
		value = 0;
	}

	IntTuple(int key, int value) {
		this.key = key;
		this.value = value;
	}

	void incrementKey(int delta) {
		key += delta;
	}

	void incrementValue(int delta) {
		value += delta;
	}

	void incrementKey() {
		key++;
	}

	void incrementValue() {
		value++;
	}

	int key() {
		return key;
	}

	int value() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() == this.getClass()) {
			final IntTuple cmp = (IntTuple) obj;
			return cmp.key == this.key && cmp.value == this.value;
		}
		return false;
	}

	boolean moreThan(IntTuple tuple) {
		return tuple.key < this.key && tuple.value < this.value;
	}

	void reset() {
		key = value = 0;
	}

	@Override
	public String toString() {
		return String.format("%d / %d", key, value);
	}
}
