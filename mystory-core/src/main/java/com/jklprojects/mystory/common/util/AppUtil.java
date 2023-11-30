/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import com.jklprojects.mystory.common.user.UserRoleType;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Application Utility.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-11-23
 * @version 2, 2018-04-23
 * @version 2, 2018-05-23
 * @version 3, 2018-10-23
 */
public class AppUtil {

	/**
	 * @param email
	 * @param userRoleType
	 * @return
	 */
	public static String createtUserNameFromEmail(String email, UserRoleType userRoleType) {
		String userName = "";
		if (StringUtil.isNotEmpty(email)) {
			userName = email.substring(0, email.indexOf('@'));
		}
		return new StringBuilder(userName).append(userRoleType.getUserRoleTypeName()).toString();
	}

	/** @return random Long for use as user uid. */
	public static Long genRandUserUID() {
		return NumUtil.genRandLong(1000000000000000000L, 9223372036854775807L);
	}

	/**
	 * Return the name of the method that is calling this method.
	 *
	 * @return the name of the method that is calling this method
	 */
	public static String getCallerMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	/**
	 * @param manifest
	 * @param manifestAttrName
	 * @return
	 * @throws IOException
	 */
	public static String getManifestAttributeValue(Manifest manifest, Attributes.Name manifestAttrName)
			throws IOException {
		String value = "";
		Attributes mainAttribs = manifest.getMainAttributes();
		value = mainAttribs.getValue(manifestAttrName);

		return value;
	}
}
