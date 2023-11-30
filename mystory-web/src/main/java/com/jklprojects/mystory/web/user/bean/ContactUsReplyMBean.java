/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.user.entity.view.ContactUsView;
import com.jklprojects.mystory.business.user.entity.view.UserCommView;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-07-31
 */
@Named("contactUsReplyMBean")
@SessionScoped
public class ContactUsReplyMBean extends AbstractAppManagedBean {

	private static final long serialVersionUID = 4477863616982429930L;

	private static final XLogger logger = XLoggerFactory.getXLogger(ContactUsReplyMBean.class);

	private Long contactUsId;

	private ContactUsView contactUs;

	private String replyText;

	public ContactUsReplyMBean() {
		super();
	}

	public Long getContactUsId() {
		return contactUsId;
	}

	public void setContactUsId(Long contactUsId) {
		this.contactUsId = contactUsId;
	}

	@PostConstruct
	public void init() {
		if (contactUsId != null) {
			contactUs = getUserService().findContactUs(contactUsId);
		}
	}

	/** @return navOutcome */
	public String doSubmitContactUsReply(ContactUsView contactUs) {
		String navOutcome = null;
		setContactUs(contactUs);
		UserCommView userCommView = getUserService().createContactUsReply(getContactUs().getId(), getReplyText());
		if (userCommView.isIdSet()) {
			navOutcome = JSFNavigation.SUCCESS.getNavigation();
			addMsg(I18N.APP.getI18N("contactus_replySuccessMsg"), "", FacesMessage.SEVERITY_INFO);
		} else {
			addMsg(I18N.APP.getI18N("contactus_replyFailureMsg"), "", FacesMessage.SEVERITY_ERROR);
		}
		return navOutcome;
	}

	public String doWriteContactUsReply(ContactUsView contactUs) {
		this.contactUs = contactUs;
		return "contactUsReply";
	}

	public ContactUsView getContactUs() {
		return contactUs;
	}

	public void setContactUs(ContactUsView contactUs) {
		this.contactUs = contactUs;
	}

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
}
