/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.common.ex.AppCodeException;

/**
 * Represents light weight view for User Entity for verifying account.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Apr 19, 2015
 */
public class VerifyAccountView extends UserView {
	private static final long serialVersionUID = 8002071703640628L;

	private String securityToken;

	public VerifyAccountView() {
		super();
	}

	@Override
	public VerifyAccountView setViewFromEntity(User user, boolean setBi) {
		super.setViewFromEntity(user, setBi);

		return this;
	}

	@Override
	public User setEntityFromView(User user, boolean setBi) throws AppCodeException {
		super.setEntityFromView(user, setBi);

		return user;
	}

	public String getSecurityToken() {
		return securityToken;
	}

	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}
}
