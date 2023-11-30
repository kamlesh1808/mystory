/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.advert;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-12-30
 * @version 2, 2018-06-23
 * @version 3, 2018-11-23
 */
public enum AdvertType {
	UNDEFINED(0, "Select One", "Selection One"), BOOK(1, "Book", "Book"), PRODUCT(2, "Product", "Product"), WEBSITE(3,
			"Website", "Website");

	private static final String SIMPLE_NAME = AdvertType.class.getSimpleName();

	private final int id;
	private final String name;
	private final String description;

	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	AdvertType(int id, String name, String description) {
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
	public static AdvertType toEnum(int id) {
		return Arrays.stream(AdvertType.values()).filter(e -> e.getId() == id).findFirst().get();
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

	public boolean isAdvertTypeBook() {
		return this == AdvertType.BOOK;
	}

	public boolean isAdvertTypeProduct() {
		return this == AdvertType.PRODUCT;
	}

	public boolean isAdvertTypeWebsite() {
		return this == AdvertType.WEBSITE;
	}
}
