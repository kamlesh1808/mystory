/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import java.util.Arrays;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-10-24
 * @version 2, 2018-06-23
 */
public enum UserCommType {
	EMAIL(1);

	private final int id;

	UserCommType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching object
	 */
	public static UserCommType toEnum(int id) {
		return Arrays.stream(UserCommType.values()).filter(e -> e.getId() == id).findFirst().get();
	}
}
