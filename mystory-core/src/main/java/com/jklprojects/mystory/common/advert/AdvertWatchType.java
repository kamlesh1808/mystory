/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.advert;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-01-23
 * @version 2, 2018-06-23
 */
public enum AdvertWatchType {
	UNDEFINED(0, "Select One", "Selection One"), CLICK(1, "Click", "Click"), VIEW(2, "View", "View");

	private static final String SIMPLE_NAME = AdvertWatchType.class.getSimpleName();

	private final int id;
	private final String name;
	private final String description;

	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	AdvertWatchType(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	/**
	 * Lookup and return matching object or null if match is not found.
	 *
	 * @param id
	 * @return matching object or null if match is not found.
	 */
	public static AdvertWatchType toEnum(int id) {
		return Arrays.stream(AdvertWatchType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	public String getDescription() {
		return description;
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(SIMPLE_NAME, id, name());
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isAdvertWatchTypeClick() {
		return this == AdvertWatchType.CLICK;
	}

	public boolean isAdvertWatchTypeView() {
		return this == AdvertWatchType.VIEW;
	}
}
