package com.deci.galaga;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
abstract class Resource {

	@Getter(AccessLevel.PACKAGE)
	private final String path;

	@Getter(AccessLevel.PACKAGE)
	private final String fileName;

	final String getFullPath() {
		return path + fileName;
	}
}
