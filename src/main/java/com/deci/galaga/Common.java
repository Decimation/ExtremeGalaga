package com.deci.galaga;

class Common {

	private Common() {
	}

	static void printf(String s, Object... fmt) {
		System.out.printf("[debug] %s\n", String.format(s, fmt));
	}

	@SuppressWarnings("unchecked")
	static <T> T cast(final Object o, Class<T> c) {
		return (T) o;
	}

	static void autoThread(final Runnable target) {
		final Thread t = new Thread(target);
		//System.out.printf("Starting autoThread %s\n", t.getName());
		t.start();
	}

	static void sleep(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static int getCurrentlyExecutingThreads() {
		int nbRunning = 0;
		for (Thread t : Thread.getAllStackTraces().keySet()) {
			if (t.getState() == Thread.State.RUNNABLE) nbRunning++;
		}
		return nbRunning;
	}
}
