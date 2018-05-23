package com.deci.galaga;

import java.util.HashMap;
import java.util.Map;

public class KBThread extends Thread implements Runnable {

	private final Map<Character, Boolean> keyMap = new HashMap<>();

	void toggleKey(char c) {
		if (keyMap.containsKey(c)) {
			keyMap.put(c, !keyMap.get(c));
		} else {
			keyMap.put(c, true);
		}
	}

	boolean getKeyState(char c) {
		if (keyMap.containsKey(c))
			return keyMap.get(c);

		keyMap.put(c, true);
		return true;
	}

	@Override
	public void run() {

	}
}
