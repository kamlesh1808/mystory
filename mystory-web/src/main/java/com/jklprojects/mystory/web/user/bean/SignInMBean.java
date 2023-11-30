/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.audit.entity.Audit;
import com.jklprojects.mystory.business.audit.entity.AuditDetail;
import com.jklprojects.mystory.business.audit.entity.AuditEntity;
import com.jklprojects.mystory.business.audit.entity.AuditEventType;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.common.audit.AuditAttrType;
import com.jklprojects.mystory.common.audit.AuditType;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.dto.SignedInUserBean;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import com.jklprojects.mystory.web.api.servlet.AppServletUtil;
import java.util.Optional;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * JSF Managed Bean for sign in page whose responsibilities include sign in and
 * sign out for Application users and OAuth2 users.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-15
 */
@Named("signInMBean")
@SessionScoped
public class SignInMBean extends AbstractAppManagedBean {

	private static final long serialVersionUID = -6490754005832885758L;

	private static final XLogger logger = XLoggerFactory.getXLogger(SignInMBean.class);

	private String email;

	private String password;

	public SignInMBean() {
		super();
	}

	/**
	 * Attempt sign in with the entered user id and password and start a user
	 * session if sign-in was successful, otherwise display error.
	 *
	 * @return navigation outcome
	 * @see JSFNavigation#SUCCESS
	 */
	public String doSignIn() {
		String navOutcome = JSFNavigation.FAILURE.getNavigation();
		UserView user = null;

		try {
			Optional<UserView> userOptional = getUserService().validateCredentials(getEmail(), getPassword());

			setPassword(null); // secure the password

			if (userOptional.isPresent()) {
				user = userOptional.get();
				logger.trace("doSignIn " + user.toString());

				HttpServletRequest req = getHttpServletRequest();
				HttpSession session = req.getSession(true);

				SignedInUserBean signedInUserBean = new SignedInUserBean(user.getId(), user.getUserName(),
						user.getUserRoleType(), String.valueOf(user.getUid()), user.getFirstName(), user.getLastName());

				AppServletUtil.setSessionAttribute(req, SIGNED_IN_USER_BEAN, signedInUserBean);

				logger.info("Session time out in minutes is {} ", getAppConfigService().getSessionTimeOutInMinutes());
				session.setMaxInactiveInterval(getAppConfigService().getSessionTimeOutInMinutes() * 60);

				if (session.isNew()) {
					logger.info("New Session signin User: userName {}, userId {}, sessionId {}", user.getUserName(),
							user.getId(), session.getId());
				}

				if (user.getStatus().isActiveTempPassword()) {
					addMsg(I18N.APP.getI18N("signin_signinTempPasswordSuccessMsg", user.getUserName()), "",
							FacesMessage.SEVERITY_INFO, true);

					navOutcome = JSFNavigation.ACCOUNT_RECOVERY.getNavigation();
				} else {
					addMsg(I18N.APP.getI18N("signin_signinSuccessMsg", user.getUserName(),
							getAppConfigService().getAppName()), "", FacesMessage.SEVERITY_INFO, true);

					navOutcome = JSFNavigation.SUCCESS.getNavigation();
				}

			} else {
				setPassword(null); // secure the password

				logger.warn("signin invalid credentials {} ", getEmail());

				addMsg(I18N.APP.getI18N("signin_signinFailureMsg"), "", FacesMessage.SEVERITY_ERROR);
				navOutcome = JSFNavigation.SUCCESS.getNavigation();
			}
		} catch (AppCodeException e) {
			setPassword(null); // secure the password

			logger.error("signin exception {} ", getEmail());

			processAppCodeException(e);
			navOutcome = JSFNavigation.SUCCESS.getNavigation();

		} finally {
			setPassword(null); // secure the password
		}

		return navOutcome;
	}

	/**
	 * Sign out of user session if it exists.
	 *
	 * @return navigation outcome
	 */
	public String doSignOut() {
		String signedInUserName = "";

		HttpSession session = getSession(false);
		if (session != null) {
			SignedInUserBean signedInUserBean = getSignedInUserBean();

			try {
				signedInUserName = signedInUserBean.getUserName();
				String signedInUserUID = signedInUserBean.getUserUID();
				UserRoleType userRoleType = signedInUserBean.getUserRoleType();
				session.invalidate();

				logger.info("signout user: signedInUserUID {}, userRoleType {}, session {} ", signedInUserName,
						signedInUserUID, userRoleType, session.getId());

				addMsg(I18N.APP.getI18N("signin_signOutSuccessMsg", signedInUserName,
						getAppConfigService().getAppName()), "", FacesMessage.SEVERITY_INFO, true);

				Audit audit = new Audit(signedInUserBean.getId(), AuditType.USER_SIGN_OUT, AuditEventType.CREATE,
						AuditEntity.USER, signedInUserUID);
				audit.addAuditDetail(
						new AuditDetail(AuditAttrType.USER_SIGN_OUT_SUCCESS.getAttrName(), Boolean.TRUE.toString()));
				getAuditService().createAudit(audit);

				return JSFNavigation.SIGNOUT_SUCCESS.getNavigation();

			} catch (Exception e) {
				addMsg(I18N.APP.getI18N("signin_signOutFailureMsg", signedInUserName), "", FacesMessage.SEVERITY_ERROR);

				logger.catching(e);

				Audit audit = new Audit(signedInUserBean.getId(), AuditType.USER_AUTHENTICATION, AuditEventType.CREATE,
						AuditEntity.USER, null);
				audit.addAuditDetail(
						new AuditDetail(AuditAttrType.USER_SIGN_OUT_SUCCESS.getAttrName(), Boolean.FALSE.toString()));
				audit.addAuditDetail(new AuditDetail(AuditAttrType.EXCEPTION_MSG.getAttrName(), e.getMessage()));

				getAuditService().createAudit(audit);
			}
		}

		return JSFNavigation.SIGNOUT_FAILURE.getNavigation();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Determine if user is already signed in, if not return sign in.
	 *
	 * @return navigation outcome
	 * @see JSFNavigation#SIGNIN
	 */
	public String userSignedIn() {
		return isUserSignedIn() ? JSFNavigation.SUCCESS.getNavigation() : JSFNavigation.SIGNIN.getNavigation();
	}
}
