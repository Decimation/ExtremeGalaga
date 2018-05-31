package com.deci.galaga;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

class Common {

	private Common() {
	}

	private static final Set<Debug> disabled = new HashSet<>();

	/**
	 * Disables logging for specified Debug type
	 * @param dbg Type of logging to disable
	 */
	static void disableLog(Debug... dbg) {
		disabled.addAll(Arrays.asList(dbg));
	}

	static void printf(String s, Object... fmt) {
		printf(Debug.STANDARD, s, fmt);
	}

	static void printf(Debug dbg, String s, Object... fmt) {
		if (!disabled.contains(dbg))
			System.out.printf("[%s] %s\n", dbg.toString().toLowerCase(), String.format(s, fmt));
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

	static int getFileSize(URL url) {
		URLConnection conn = null;
		try {
			conn = url.openConnection();
			if (conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).setRequestMethod("HEAD");
			}
			conn.getInputStream();
			return conn.getContentLength();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn instanceof HttpURLConnection) {
				((HttpURLConnection) conn).disconnect();
			}
		}
	}
}
