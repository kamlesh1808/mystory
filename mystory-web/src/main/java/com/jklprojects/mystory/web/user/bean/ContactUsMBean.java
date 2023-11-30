/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.user.entity.view.ContactUsView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.user.ContactUsStatus;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-07-29
 */
@Named("contactUsMBean")
@RequestScoped
public class ContactUsMBean extends AbstractAppManagedBean {
	private static final long serialVersionUID = -6357487586597196199L;

	private static final XLogger logger = XLoggerFactory.getXLogger(ContactUsMBean.class);

	private ContactUsView contactUs = new ContactUsView();
	private ContactUsView contactUsReply;

	@Inject
	private SignInMBean signin;

	private List<ContactUsView> contactUsForReview = new ArrayList<>();

	public ContactUsMBean() {
		super();
	}

	@PostConstruct
	public void init() {
		contactUs = new ContactUsView();

		if (getSignin() != null && getSignin().isSignedInUserRoleTypeAppAdmin()) {
			contactUsForReview = getUserService().findContactUsWithStatus(ContactUsStatus.NEW);
		}
	}

	public ContactUsView getContactUs() {
		return contactUs;
	}

	public void setContactUs(ContactUsView contactUs) {
		this.contactUs = contactUs;
	}

	public SignInMBean getSignin() {
		return signin;
	}

	public void setSignin(SignInMBean signin) {
		this.signin = signin;
	}

	/**
	 * Submit contact us details.
	 *
	 * @return navOutcome
	 */
	public String doContactUs() {
		String navOutcome = null;

		try {
			contactUs = getUserService().createContactUs(getContactUs());

			if (isValidUpdate(contactUs)) {
				addMsg(I18N.APP.getI18N("contactus_successMsg"), "", FacesMessage.SEVERITY_INFO);

				navOutcome = JSFNavigation.SUCCESS.getNavigation();
			}
		} catch (AppCodeException e) {
			logger.catching(e);
			navOutcome = JSFNavigation.FAILURE.getNavigation();
			addMsg(I18N.APP.getI18N("contactus_failureMsg"), "", FacesMessage.SEVERITY_ERROR);
		}

		return navOutcome;
	}

	public ContactUsView getContactUsReply() {
		return contactUsReply;
	}

	public void setContactUsReply(ContactUsView contactUsReply) {
		this.contactUsReply = contactUsReply;
	}

	public List<ContactUsView> getContactUsForReview() {
		return contactUsForReview;
	}
}
