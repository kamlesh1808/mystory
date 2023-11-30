/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.advert;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-12-31
 * @version 2, 2018-06-23
 * @version 3, 2018-08-23
 */
public enum AdvertPriorityType {
	TOP(500, "Top", "Top"), HIGH(400, "High", "High"), MEDIUM(300, "Medium", "Medium"), LOW(200, "Low",
			"Low"), VERY_LOW(100, "Very Low", "Very Low"), UNDEFINED(0, "Select One", "Selection One");

	private static final String SIMPLE_NAME = AdvertPriorityType.class.getSimpleName();

	private final int priority;
	private final String name;
	private final String description;

	/**
	 * @param priority
	 * @param name
	 * @param description
	 */
	AdvertPriorityType(int priority, String name, String description) {
		this.priority = priority;
		this.name = name;
		this.description = description;
	}

	/**
	 * Lookup and return matching object or null if match is not found.
	 *
	 * @param priority
	 * @return matching object or null if match is not found.
	 */
	public static AdvertPriorityType toEnum(int priority) {
		return Arrays.stream(AdvertPriorityType.values()).filter(e -> e.getPriority() == priority).findFirst().get();
	}

	public String getDescription() {
		return description;
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(SIMPLE_NAME, priority, name());
	}

	public String getName() {
		return name;
	}

	public int getPriority() {
		return priority;
	}

	public boolean isAdvertPriorityTypeHigh() {
		return this == AdvertPriorityType.HIGH;
	}

	public boolean isAdvertPriorityTypeLow() {
		return this == AdvertPriorityType.LOW;
	}

	public boolean isAdvertPriorityTypeMedium() {
		return this == AdvertPriorityType.MEDIUM;
	}

	public boolean isAdvertPriorityTypeTop() {
		return this == AdvertPriorityType.TOP;
	}
}
