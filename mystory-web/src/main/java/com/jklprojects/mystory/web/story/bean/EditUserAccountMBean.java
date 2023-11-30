/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.user.UserStatus;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-15
 */
@Named("editUserAccountMBean")
@SessionScoped
public class EditUserAccountMBean extends AbstractAppManagedBean {

	private static final long serialVersionUID = -3849722656823843980L;

	// use View for UI comp binding
	private UserView user;

	private UserOAuth2View userOAuth2;

	public EditUserAccountMBean() {
	}

	@PostConstruct
	public void init() {
		try {
			if (isSignedInUserTypeAppInternal()) {
				long singedInUserUID = Long.parseLong(getSignedInUserUID());
				user = getUserService().findUserViewWithUserUID(singedInUserUID);

			} else if (isSignedInUserTypeOAuth2External()) {
				userOAuth2 = getUserOAuth2Service().findUserOAuthViewWithOAuthUID(getSignedInUserUID());
			}
		} catch (AppCodeException e) {
			processAppCodeException(e);
		} catch (NumberFormatException e) {
			processAppException(new AppException(e.getMessage(), e));
		}
	}

	public UserView getUser() {
		return user;
	}

	public void setUser(UserView user) {
		this.user = user;
	}

	public String doUpdateUserAccount() {
		String navOutcome = JSFNavigation.FAILURE.getNavigation();

		try {
			user = getUserService().updateUserAccount(user);

			addMsg(I18N.APP.getI18N("useraccount_userUpdatedSucesssMsg", user.getUserName()), "",
					FacesMessage.SEVERITY_INFO, true);

			return user != null ? JSFNavigation.SUCCESS.getNavigation() : JSFNavigation.FAILURE.getNavigation();

		} catch (AppCodeException e) {
			processAppCodeException(e);
		}

		return navOutcome;
	}

	public UserOAuth2View getUserOAuth2() {
		return userOAuth2;
	}

	public void setUserOAuth2(UserOAuth2View userOAuth2) {
		this.userOAuth2 = userOAuth2;
	}

	public String doUpdateUserOAuth2Account() {
		String navOutcome = JSFNavigation.FAILURE.getNavigation();

		try {
			userOAuth2 = getUserOAuth2Service().updateUserOAuth2Account(userOAuth2);

			addMsg(I18N.APP.getI18N("useraccount_userUpdatedSucesssMsg", userOAuth2.getUserName()), "",
					FacesMessage.SEVERITY_INFO, true);

			return userOAuth2 != null ? JSFNavigation.SUCCESS.getNavigation() : JSFNavigation.FAILURE.getNavigation();

		} catch (AppCodeException e) {
			processAppCodeException(e);
		}

		return navOutcome;
	}

	public boolean isUserStatusPendingEMailVerify() {
		return user != null && UserStatus.PENDING_EMAIL_VERIFY == user.getStatus();
	}
}
