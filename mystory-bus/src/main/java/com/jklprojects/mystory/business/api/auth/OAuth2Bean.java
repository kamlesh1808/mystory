/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.auth;

import java.io.Serializable;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Dec-23
 */
public class OAuth2Bean implements Serializable {

	private static final long serialVersionUID = 1466443781524210053L;

	private String id;
	private String userName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
