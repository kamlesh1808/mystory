/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service;

import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.business.email.ejb.EMailerService;
import com.jklprojects.mystory.business.user.entity.UserComm;
import com.jklprojects.mystory.business.user.entity.UserCommAttr;
import com.jklprojects.mystory.business.user.service.api.UserCommService;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.user.UserCommAttrName;
import com.jklprojects.mystory.common.user.UserCommName;
import com.jklprojects.mystory.common.user.UserCommStatus;
import com.jklprojects.mystory.report.api.AppJasperReport;
import com.jklprojects.mystory.report.api.IReportConsts;
import com.jklprojects.mystory.report.story.StoryReportBean;
import com.jklprojects.mystory.report.user.AccountReportBean;
import com.jklprojects.mystory.report.user.ContactUsReportBean;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * User Communication Services to find, prepare, and send them at a automatic
 * scheduled interval.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-10-30
 */
@Singleton
@Startup
public class UserCommServiceImpl extends AppEntityDAO<UserComm> implements UserCommService {
	private static final XLogger logger = XLoggerFactory.getXLogger(UserCommServiceImpl.class);

	@Inject
	private EMailerService eMailService;

	public UserCommServiceImpl() {
		super(UserComm.class);
	}

	@Override
	public UserComm createUserComm(UserComm userComm) {
		return create(userComm);
	}

	/**
	 * Find user communications entities with given status.
	 *
	 * @param status
	 */
	@Override
	public List<UserComm> findUserComms(UserCommStatus... status) {
		return getEM().createNamedQuery("findUserComms", UserComm.class).setParameter("status", Arrays.asList(status))
				.getResultList();
	}

	/**
	 * Find user communications, prepare them in a form of a report if needed, and
	 * send them, either as an EMail or other forms of communications, at a
	 * scheduled time.
	 */
	@Override
	@Schedule(second = "*/50", minute = "*", hour = "*", persistent = false)
	public void sendUserComms() {
		logger.trace("START sending user communications");

		List<UserComm> userComms = findUserComms(UserCommStatus.NEW, UserCommStatus.PENDING_SEND);

		for (UserComm uc : userComms) {
			UserCommName userCommName = uc.getUserCommName();
			boolean success = false;
			if (userCommName.isUserCommTypeEMail()) {
				if (userCommName == UserCommName.ACCOUNT_VERIFY) {
					success = sendEMailAccount(uc, AppJasperReport.ACCOUNT_VERIFY);
				} else if (userCommName == UserCommName.ACCOUNT_VERIFIED) {
					success = sendEMailAccount(uc, AppJasperReport.ACCOUNT_VERIFIED);
				} else if (userCommName == UserCommName.ACCOUNT_RECOVERY) {
					success = sendEMailAccount(uc, AppJasperReport.ACCOUNT_RECOVERY);
				} else if (userCommName == UserCommName.PASSWORD_CHANGED) {
					success = sendEMailAccount(uc, AppJasperReport.PASSWORD_CHANGED);
				} else if (userCommName == UserCommName.VERIFY_EMAIL_UPDATE) {
					success = sendEMailAccount(uc, AppJasperReport.VERIFY_EMAIL_UPDATE);
				} else if (userCommName == UserCommName.CONTACTUS_CREATED) {
					success = sendEMailContactUs(uc, AppJasperReport.CONTACTUS_CREATED);
				} else if (userCommName == UserCommName.CONTACTUS_REPLY) {
					success = sendEMailContactUs(uc, AppJasperReport.CONTACTUS_REPLY);
				} else if (userCommName == UserCommName.STORY_CREATED) {
					success = sendEMailStory(uc, AppJasperReport.STORY_CREATED);
				} else if (userCommName == UserCommName.STORY_REVIEW) {
					success = sendEMailStory(uc, AppJasperReport.STORY_REVIEW);
				} else if (userCommName == UserCommName.STORY_REVIEWED) {
					success = sendEMailStory(uc, AppJasperReport.STORY_REVIEWED);
				}
			}

			if (success) {
				uc.setStatus(UserCommStatus.SENT);
				update(uc);
			}
		}
		if (userComms.size() > 0) {
			logger.info("COMPLETED sending user communications. Processed {} ", userComms.size());
		}
	}

	public EMailerService getEMailService() {
		return eMailService;
	}

	private boolean sendEMailAccount(UserComm userComm, AppJasperReport report) {
		AccountReportBean reportBean = new AccountReportBean();
		boolean success = false;
		String subject = "";
		String emailTo = "";

		for (UserCommAttr uca : userComm.getAttrs()) {
			if (uca.getAttrName() == UserCommAttrName.EMAIL_SUBJECT) {
				subject = uca.getValue();
			} else if (uca.getAttrName() == UserCommAttrName.EMAIL_TO) {
				emailTo = uca.getValue();
			} else if (uca.getAttrName() == UserCommAttrName.EMAIL) {
				reportBean.setEmail(uca.getValue());
			} else if (uca.getAttrName() == UserCommAttrName.EMAIL_OLD) {
				reportBean.setEmailOld(uca.getValue());
			} else if (uca.getAttrName() == UserCommAttrName.USERNAME) {
				reportBean.setUserName(uca.getValue());
			} else if (uca.getAttrName() == UserCommAttrName.EMAIL_VERIFY_TOKEN) {
				reportBean.setSecurityToken(uca.getValue());
			} else if (uca.getAttrName() == UserCommAttrName.ACCOUNT_TEMP_PWD) {
				reportBean.setTempPassword(uca.getValue());
			}
		}

		try {
			report.bindReportImageResourceParam(IReportConsts.MYSTORY_LOGO,
					getAppConfigService().getReportMyStoryLogo()).bindReportImageResourceParam(
							IReportConsts.MYSTORY_ICON, getAppConfigService().getReportMyStoryIcon());

			String html = report.generateReportToString(reportBean);

			getEMailService().sendEmail(Arrays.asList(emailTo), subject, html, null);
			success = true;
		} catch (AppCodeException e) {
			logger.catching(e);
			success = false;
		} catch (AppException e) {
			logger.catching(e);
			success = false;
		}
		return success;
	}

	private boolean sendEMailContactUs(UserComm userComm, AppJasperReport report) {
		ContactUsReportBean reportBean = new ContactUsReportBean();
		boolean success = false;
		String subject = "";
		String emailTo = "";

		for (UserCommAttr uca : userComm.getAttrs()) {
			if (uca.getAttrName() == UserCommAttrName.EMAIL_SUBJECT) {
				subject = uca.getValue();
			} else if (uca.getAttrName() == UserCommAttrName.EMAIL_TO) {
				emailTo = uca.getValue();
			} else if (uca.getAttrName() == UserCommAttrName.EMAIL) {
				reportBean.setEmail(uca.getValue());
			} else if (uca.getAttrName() == UserCommAttrName.USERNAME) {
				reportBean.setUserName(uca.getValue());
			} else if (uca.getAttrName() == UserCommAttrName.FIRST_NAME) {
				reportBean.setFirstName(uca.getValue());
			} else if (uca.getAttrName() == UserCommAttrName.LAST_NAME) {
				reportBean.setLastName(uca.getValue());
			} else if (uca.getAttrName() == UserCommAttrName.CONTACT_US_ID) {
				reportBean.setContactUsId(uca.getValue());
			} else if (uca.getAttrName() == UserCommAttrName.CONTACT_US_REPLY_TEXT) {
				reportBean.setReplyText(uca.getValue());
			}
		}

		try {
			report.bindReportImageResourceParam(IReportConsts.MYSTORY_LOGO,
					getAppConfigService().getReportMyStoryLogo()).bindReportImageResourceParam(
							IReportConsts.MYSTORY_ICON, getAppConfigService().getReportMyStoryIcon());

			String html = report.generateReportToString(reportBean);

			getEMailService().sendEmail(Arrays.asList(emailTo), subject, html, null);
			success = true;
		} catch (AppCodeException e) {
			logger.catching(e);
			success = false;
		} catch (AppException e) {
			logger.catching(e);
			success = false;
		}

		return success;
	}

	private boolean sendEMailStory(UserComm userComm, AppJasperReport report) {
		StoryReportBean reportBean = new StoryReportBean();
		String subject = "";
		String emailTo = "";
		boolean success = false;

		for (UserCommAttr uca : userComm.getAttrs()) {
			String value = uca.getValue();

			if (uca.getAttrName() == UserCommAttrName.EMAIL_SUBJECT) {
				subject = value;
			} else if (uca.getAttrName() == UserCommAttrName.EMAIL_TO) {
				emailTo = value;
			} else if (uca.getAttrName() == UserCommAttrName.USERNAME) {
				reportBean.setUserName(value);
			} else if (uca.getAttrName() == UserCommAttrName.STORY_NAME) {
				reportBean.setStoryName(value);
			} else if (uca.getAttrName() == UserCommAttrName.STORY_PENDING_REVIEW_MESSAGE) {
				reportBean.setStoryPendingReviewMessage(value);
			} else if (uca.getAttrName() == UserCommAttrName.STORY_TITLE) {
				reportBean.setStoryTitle(value);
			} else if (uca.getAttrName() == UserCommAttrName.STORY_STATUS) {
				reportBean.setStoryStatus(value);
			} else if (uca.getAttrName() == UserCommAttrName.STORY_TYPE) {
				reportBean.setStoryType(value);
			} else if (uca.getAttrName() == UserCommAttrName.STORY_ACCESS_TYPE) {
				reportBean.setStoryAccessType(value);
			}
		}

		try {
			report.bindReportImageResourceParam(IReportConsts.MYSTORY_LOGO,
					getAppConfigService().getReportMyStoryLogo()).bindReportImageResourceParam(
							IReportConsts.MYSTORY_ICON, getAppConfigService().getReportMyStoryIcon());

			String html = report.generateReportToString(reportBean);

			getEMailService().sendEmail(Arrays.asList(emailTo), subject, html, null);
			success = true;
		} catch (AppCodeException e) {
			logger.catching(e);
			success = false;
		} catch (AppException e) {
			logger.catching(e);
			success = false;
		}
		return success;
	}
}
