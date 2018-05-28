package com.deci.galaga;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Assets {
	private Assets() {}

	private static final Map<String, Resource> resources = new HashMap<>();

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
