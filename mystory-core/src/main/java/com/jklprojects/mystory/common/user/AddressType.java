/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import com.jklprojects.mystory.common.i18n.I18N;
import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-07-04
 * @version 2, 2018-06-23
 */
public enum AddressType {
	UNDEFINED(0, "Select One", "Selection One"), HOME(1, "Home", "Home"), MAILING(2, "Mailing", "Mailing");

	private static final String SIMPLE_NAME = AddressType.class.getSimpleName();

	private int id;
	private String typeName;
	private String description;

	AddressType(int id, String typeName, String description) {
		this.id = id;
		this.typeName = typeName;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name();
	}

	public String getI18N() {
		return I18N.APP_ENUMS.getI18N(SIMPLE_NAME, id, name());
	}

	public String getTypeName() {
		return typeName;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching status
	 */
	public static AddressType toEnum(int id) {
		return Arrays.stream(AddressType.values()).filter(e -> e.getId() == id).findFirst().get();
	}

	/**
	 * Return true if the object equals one in the given list, false otherwise.
	 *
	 * @param list
	 * @return true if object equals one in the given list, false otherwise.
	 */
	public boolean in(AddressType... list) {
		return Arrays.stream(list).filter(e -> e.getId() == id).findFirst().isPresent();
	}
}
