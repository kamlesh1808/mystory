/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-15
 */
@Named("userAccountRecoveryMBean")
@RequestScoped
public class UserAccountRecoveryMBean extends AbstractAppManagedBean {
	private static final long serialVersionUID = 3676996812680385545L;

	private String email;

	public UserAccountRecoveryMBean() {
		super();
	}

	public String doAccountRecovery() {
		String navOutcome = JSFNavigation.FAILURE.getNavigation();

		try {
			UserView userView = getUserService().resetToTempPassword(email);
			if (userView != null && userView.isIdSet()) {
				addMsg(I18N.APP.getI18N("useraccount_recoveryEMailSucesssMsg", email), "", FacesMessage.SEVERITY_INFO,
						true);
				navOutcome = JSFNavigation.SUCCESS.getNavigation();
			} else {
				addMsg(I18N.APP.getI18N("useraccount_recoveryEMailNotFound", email), "", FacesMessage.SEVERITY_ERROR,
						true);
				return JSFNavigation.FAILURE.getNavigation();
			}
		} catch (AppCodeException e) {
			processAppCodeException(e);
		}

		return navOutcome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
