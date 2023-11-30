/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.user.entity.view.UserChangePasswordView;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-16
 */
@Named("changePasswordMBean")
@RequestScoped
public class ChangePasswordMBean extends AbstractAppManagedBean {

	private static final long serialVersionUID = -7756365197557037856L;

	// use View for UI comp binding
	private UserChangePasswordView user;

	public ChangePasswordMBean() {
		super();
	}

	public UserChangePasswordView getUser() {
		return user;
	}

	public void setUser(UserChangePasswordView user) {
		this.user = user;
	}

	@PostConstruct
	public void init() {
		try {
			long singedInUserUID = Long.parseLong(getSignedInUserUID());
			user = getUserService().findUserWithUserUID(singedInUserUID, UserChangePasswordView.class);
		} catch (AppCodeException e) {
			processAppCodeException(e);
		} catch (NumberFormatException e) {
			processAppException(new AppException(e.getMessage(), e));
		}
	}

	public String doUpdatePassword() {
		String navOutcome = JSFNavigation.FAILURE.getNavigation();
		try {
			UserView updatedUser = getUserService().updatePassword(user);

			addMsg(I18N.APP.getI18N("useraccount_changePasswordSucesssMsg", user.getUserName()), "",
					FacesMessage.SEVERITY_INFO);

			return updatedUser != null ? JSFNavigation.SUCCESS.getNavigation() : JSFNavigation.FAILURE.getNavigation();

		} catch (AppCodeException e) {
			processAppCodeException(e);
		}
		return navOutcome;
	}
}
