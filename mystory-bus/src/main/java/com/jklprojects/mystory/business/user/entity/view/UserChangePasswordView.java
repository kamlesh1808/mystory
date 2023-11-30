/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.common.ex.AppCodeException;

/**
 * Represents light weight view for User Entity for changing password.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-16
 * @version 1, 2017-01-23
 * @version 3, 2018-12-23
 */
public class UserChangePasswordView extends UserView {
	private static final long serialVersionUID = 8002071703640628L;

	private String currentPassword;
	private String newPassword;
	private String confirmPassword;

	public UserChangePasswordView() {
		super();
	}

	@Override
	public UserChangePasswordView setViewFromEntity(User user, boolean setBi) {
		super.setViewFromEntity(user, setBi);

		return this;
	}

	@Override
	public User setEntityFromView(User user, boolean setBi) throws AppCodeException {
		super.setEntityFromView(user, setBi);
		user.setPassword(getNewPassword());

		return user;
	}

	@Override
	public String toString() {
		return "UserChangePasswordView [currentPassword=" + currentPassword + ", newPassword=" + newPassword
				+ ", confirmPassword=" + confirmPassword + ", toString()=" + super.toString() + "]";
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
