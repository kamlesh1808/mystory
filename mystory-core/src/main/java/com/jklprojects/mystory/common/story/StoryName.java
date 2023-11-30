/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * A Story Name is a general topic name for a story. A set of Story Names are
 * pre-defined here.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @see app_enum.properties
 */
public enum StoryName {

	/**
	 * Please do not change Story Name constants name, id, or order, without
	 * justification.
	 */
	UNDEFINED(0, "Select One", "Selection One"), BE_HEALED(1, "Be Healed!", "Be Healed!"), BITS_AND_BYTES(2,
			"Bits and Bytes", "Bits and Bytes"), EAT_WELL(3, "Eat Well", "Eat Well"), GRATITUDE_SHARED(4,
					"Gratitude Shared",
					"Gratitude Shared"), HEALTHY_BODY(5, "Healthy Body", "Healthy Mind"), HEALTHY_MIND(6,
							"Healthy Mind", "Healthy Mind"), LIFE_LOVED(7, "Life Loved", "Life Loved"), LINKED_IN(8,
									"Linked In", "Linked In"), UNIVERSAL_CONNECTION(9, "Universal Connection",
											"Universal Connection");

	private static final String SIMPLE_NAME = StoryName.class.getSimpleName();

	private final int id;
	private final String storyName;
	private String storyDesc;

	StoryName(int id, String storyName, String storyDesc) {
		this.id = id;
		this.storyName = storyName;
		this.storyDesc = storyDesc;
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching status
	 */
	public static StoryName toEnum(int id) {
		return Arrays.stream(StoryName.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return
	 */
	public static StoryName toEnum(String id) {
		return toEnum(Integer.parseInt(id));
	}

	/**
	 * Return true if status id is equal, false otherwise.
	 *
	 * @param id
	 * @return true if status id is equal, false otherwise.
	 */
	public boolean isIdEqual(int id) {
		return id == getId();
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(SIMPLE_NAME, id, name());
	}

	public String getI18NDescription() {
		return I18N.APP_ENUMS.getI18N(SIMPLE_NAME, id, name() + ".DESC");
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name();
	}

	public String getStoryDesc() {
		return storyDesc;
	}

	public String getStoryName() {
		return storyName;
	}

	public boolean in(StoryName... storyNames) {
		return Arrays.stream(storyNames).anyMatch(e -> e.getId() == id);
	}
}
