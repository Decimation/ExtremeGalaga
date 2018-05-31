package com.deci.galaga;

import javafx.util.Pair;
import lombok.SneakyThrows;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

// todo: add compatibility to cache other things
class Cache {
	private Cache() {}

	private static final Map<URL, Integer> internalCache = new HashMap<>();

	/**
	 * Retrieve the cached object. If the object requested was not cached,
	 * it will be automatically cached then returned.
	 */
	static Integer get(final URL url) {
		if (internalCache.containsKey(url)) {
			Common.printf(Debug.CACHE, "Retrieving cached object [%s]", url.toString());
			return internalCache.get(url);
		}
		else {
			try {
				add(url, Common.getFileSize(url));
			} catch (Exception x) {
				x.printStackTrace();
			}


			Common.printf(Debug.CACHE, "Requested object [%s] was not cached, caching key and value and returning", url.toString());
			return internalCache.get(url);
		}
	}

	static void add(URL url, int i) {
		internalCache.put(url, i);
	}
}
