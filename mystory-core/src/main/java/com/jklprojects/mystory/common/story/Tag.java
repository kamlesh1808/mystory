/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-11-20
 * @version 2, 2016-12-12
 */
public enum Tag {
	INTRODUCTION(1, "Introduction"), SUPPORT(6, "Support"), COVID19(71, "COVID-19");

	private final long id;
	private final String name;

	Tag(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public final long getId() {
		return id;
	}

	public final String getName() {
		return name;
	}
}
