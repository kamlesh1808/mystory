/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.advert;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-12-30
 * @version 2, 2018-06-23
 */
public enum AdvertAttrType {
	UNDEFINED(0, "Select One", "Selection One"), HEADER(1, "Header", "Header"), TITLE(2, "Title", "Title"), SUB_TITLE(3,
			"Sub Title", "Sub Title"), LINK(4, "Link", "Link"), IMAGE_SRC(5, "Image Src", "Image Src"), STORY_NAME(6,
					"Story Name", "Story Name"), TOOLTIP(7, "Tooltip",
							"Tooltip"), IMAGE_PATH_LOCAL(8, "Image Path Local", "Image Path Local");

	private static final String SIMPLE_NAME = AdvertAttrType.class.getSimpleName();

	private final int id;
	private final String name;
	private final String description;

	/**
	 * @param id
	 * @param name
	 * @param description
	 */
	AdvertAttrType(int id, String name, String description) {
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
	public static AdvertAttrType toEnum(int id) {
		return Arrays.stream(AdvertAttrType.values()).filter(e -> e.getId() == id).findFirst().get();
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

	public boolean isAdvertAttrTypeHeader() {
		return this == AdvertAttrType.HEADER;
	}

	public boolean isAdvertAttrTypeTitle() {
		return this == AdvertAttrType.TITLE;
	}

	public boolean isAdvertAttrTypeSubTitle() {
		return this == AdvertAttrType.SUB_TITLE;
	}

	public boolean isAdvertAttrTypeLink() {
		return this == AdvertAttrType.LINK;
	}

	public boolean isAdvertAttrTypeImageSrc() {
		return this == AdvertAttrType.IMAGE_SRC;
	}

	public boolean isAdvertAttrTypeStoryName() {
		return this == AdvertAttrType.STORY_NAME;
	}
}
