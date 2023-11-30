/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Feb 21, 2016
 */
@WebService
public class UserWS {

	@WebMethod
	public String getUserName(long userId) {
		return "userName";
	}
}
