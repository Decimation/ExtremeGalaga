package com.deci.galaga;

import java.util.HashMap;
import java.util.Map;

class Assets {
	private static final Map<String, Resource> resources = new HashMap<>();

	private static final String GITHUB_ASSETS_ROOT = "https://raw.githubusercontent.com/jsvana/galaga/master/assets/";
	static final String GITHUB_ASSETS_IMAGES = GITHUB_ASSETS_ROOT + "images/";
	static final String GITHUB_ASSETS_SOUNDS = GITHUB_ASSETS_ROOT + "sounds/";

	private Assets() {
	}

	static AudioResource getSound(String fileName) {
		return (AudioResource) resources.get(fileName);
	}

	static ImageResource getImage(String fileName) {
		return (ImageResource) resources.get(fileName);
	}

	static void add(Resource r) {
		resources.put(r.getFileName(), r);
	}
}
