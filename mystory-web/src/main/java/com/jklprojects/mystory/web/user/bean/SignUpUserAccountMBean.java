/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.util.AppUtil;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

/**
 * @author Kamleshkumar Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-14
 */
@Named("signUpUserAccountMBean")
@RequestScoped
public class SignUpUserAccountMBean extends AbstractAppManagedBean {

	private static final long serialVersionUID = 8221673702813571797L;

	private String confirmPassword;

	private UserView user = new UserView();

	public SignUpUserAccountMBean() {
		super();
	}

	@PostConstruct
	public void init() {
		user = new UserView();
		user.setUserRoleType(UserRoleType.APP_USER);
	}

	/**
	 * Do sign up with error handling left to the aggregated service API.
	 *
	 * @return navOutcome
	 */
	public String doSignUpWithThrow() {
		String navOutcome = JSFNavigation.FAILURE.getNavigation();

		try {
			user.setUid(AppUtil.genRandUserUID());
			user = getUserService().createUser(user);

			if (user != null && user.isIdSet()) {
				addMsg(I18N.APP.getI18N("useraccount_signUpSuccessMsg", user.getUserName()), "",
						FacesMessage.SEVERITY_INFO, true);

				navOutcome = JSFNavigation.SUCCESS.getNavigation();
			}

		} catch (AppCodeException e) {
			processAppCodeException(e);
		}

		return navOutcome;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public UserView getUser() {
		return user;
	}

	public void setUser(UserView user) {
		this.user = user;
	}
}
