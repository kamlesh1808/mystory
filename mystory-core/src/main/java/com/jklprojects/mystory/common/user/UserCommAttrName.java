/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.user;

import java.util.Arrays;

/**
 * Represents an identifiable User Communication Attributes Name which form the
 * content of various user communications.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-10-26
 * @version 2, 2018-06-23
 * @version 3, 2018-12-23
 */
public enum UserCommAttrName {
	EMAIL_VERIFY_TOKEN(1, "E-Mail Verify Token"), ACCOUNT_TEMP_PWD(2, "Account Temporary Password"), EMAIL(3,
			"EMail"), USERNAME(4, "User Name"), FIRST_NAME(5, "First Name"), LAST_NAME(6, "Last Name"), CONTACT_US_ID(7,
					"Contact Us Id"), CONTACT_US_REPLY_TEXT(8, "Contact Us Reply Text"), EMAIL_SUBJECT(9,
							"EMail Subject"), STORY_TITLE(10, "Story Title"), STORY_TYPE(11,
									"Story Type"), STORY_STATUS(12, "Story Status"), STORY_NAME(13,
											"Story Name"), STORY_ACCESS_TYPE(14, "Story Access Type"), EMAIL_TO(15,
													"To EMail"), EMAIL_OLD(16,
															"OLD EMail"), STORY_PENDING_REVIEW_MESSAGE(17,
																	"Story Pending Review Message");

	private int id;
	private String name;

	UserCommAttrName(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	/**
	 * Lookup and return matching object
	 *
	 * @param id
	 * @return matching object
	 */
	public static UserCommAttrName toEnum(int id) {
		return Arrays.stream(UserCommAttrName.values()).filter(e -> e.getId() == id).findFirst().get();
	}
}
