package com.deci.galaga;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
abstract class Resource {

	private static final String NULL_RESOURCE = "N/A";
	@Getter(AccessLevel.PACKAGE)
	private final        String path;
	@Getter(AccessLevel.PACKAGE)
	private final        String fileName;

	Resource() {
		path = NULL_RESOURCE;
		fileName = NULL_RESOURCE;
		//Common.printf(Debug.RESOURCE, "Warning: null resource created");
	}

	final String getFullPath() {
		return path + fileName;
	}
}
