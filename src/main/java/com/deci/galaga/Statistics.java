package com.deci.galaga;

class Statistics {

	private static int kills;

	private Statistics() {
	}

	static void recordKill() {
		kills++;
	}
}
