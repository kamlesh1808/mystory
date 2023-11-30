/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user;

import java.util.Arrays;
import java.util.List;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-19
 */
public interface UserConsts {

	int USER_EMAIL_VERIFY_TOKEN_SIZE = 8;
	int USER_TEMP_PASSWORD_SIZE = 8;

	long USER_UID_BITS_N_BYTES = 9154641512130835456L;
	String USER_UID_MYSTORY_WORK = "114636521935968680339";

	static List<Long> getStaffUsers() {
		return Arrays.asList(USER_UID_BITS_N_BYTES);
	}

	static List<String> getStaffUsersOAuth() {
		return Arrays.asList(USER_UID_MYSTORY_WORK);
	}
}
