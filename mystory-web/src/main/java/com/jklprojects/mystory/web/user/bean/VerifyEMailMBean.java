/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.user.entity.view.VerifyAccountView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.user.UserStatus;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2020-04-23
 */
@Named("verifyEMailMBean")
@RequestScoped
public class VerifyEMailMBean extends AbstractAppManagedBean {
	private static final long serialVersionUID = 2419194259261762715L;

	private final static XLogger logger = XLoggerFactory.getXLogger(VerifyEMailMBean.class);

	private VerifyAccountView user;

	public VerifyEMailMBean() {
		super();
	}

	@PostConstruct
	public void init() {
		try {
			long singedInUserUID = Long.parseLong(getSignedInUserUID());
			user = getUserService().findUserWithUserUID(singedInUserUID, VerifyAccountView.class);
		} catch (AppCodeException e) {
			processAppCodeException(e);
		} catch (NumberFormatException e) {
			processAppException(new AppException(e.getMessage(), e));
		}
	}

	public VerifyAccountView getUser() {
		return user;
	}

	public void setUser(VerifyAccountView user) {
		this.user = user;
	}

	public boolean isUserStatusPendingEMailVerify() {
		return user != null && UserStatus.PENDING_EMAIL_VERIFY == user.getStatus();
	}

	public String doVerifyAccount() throws AppCodeException {
		String navOutcome = JSFNavigation.FAILURE.getNavigation();
		logger.trace("verify account UID:" + user.getUid());
		if (getUserService().verifyAccount(user.getUid(), user.getSecurityToken())) {
			addMsg(I18N.APP.getI18N("useraccount_emailVerifySucessMsg", user.getEmail()), "",
					FacesMessage.SEVERITY_INFO);

			return redirectToCurrentPage();
		} else {
			addMsg(I18N.APP.getI18N("useraccount_emailVerifyFailureMsg", user.getEmail()), "",
					FacesMessage.SEVERITY_INFO);
		}

		return navOutcome;
	}
}
