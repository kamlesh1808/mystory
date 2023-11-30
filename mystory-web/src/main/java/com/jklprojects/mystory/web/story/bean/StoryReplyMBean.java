/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

import com.jklprojects.mystory.business.story.entity.view.StoryReplyView;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.util.StringUtil;
import com.jklprojects.mystory.common.util.TimeUtil;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-06-11
 */
@Named("storyReplyMBean")
@ViewScoped
public class StoryReplyMBean extends AbstractAppManagedBean {

	private static final long serialVersionUID = 8694182172459427927L;

	private static final XLogger logger = XLoggerFactory.getXLogger(StoryReplyMBean.class);

	private String reply;

	@Inject
	private StoriesMBean storiesMBean;

	public StoryReplyMBean() {
	}

	@PostConstruct
	public void init() {
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	/**
	 * Do story reply.
	 *
	 * @return navOutcome
	 */
	public String doStoryReply(long replyId) {
		logger.entry(replyId);

		if (!StringUtil.isEmpty(getReply())) {
			StoryView viewStory;
			try {
				viewStory = getStoryService().createStoryReply(getSignedInUserUIDLong(), storiesMBean.getStory(),
						getReply(), replyId);
				storiesMBean.setStory(viewStory);
			} catch (AppCodeException e) {
				processAppCodeException(e);
			}
		} else {
			logger.error("Story reply was null or empty");
		}

		return redirectToCurrentPage();
	}

	/**
	 * Do story reply Edit
	 *
	 * @return navOutcome
	 */
	public String doStoryReplyEdit(long replyId) {
		logger.entry(replyId);

		if (!StringUtil.isEmpty(getReply())) {
			StoryView viewStory;
			try {
				viewStory = getStoryService().editStoryReply(getSignedInUserUIDLong(), storiesMBean.getStory(),
						getReply(), replyId);
				storiesMBean.setStory(viewStory);
			} catch (AppCodeException e) {
				processAppCodeException(e);
			}
		} else {
			logger.error("Story reply was null or empty");
		}

		return redirectToCurrentPage();
	}

	public Collection<StoryStatus> getStoryStatuses() {
		return Arrays.asList(StoryStatus.values());
	}

	public StoriesMBean getStoriesMBean() {
		return storiesMBean;
	}

	public void setStoriesMBean(StoriesMBean storiesMBean) {
		this.storiesMBean = storiesMBean;
	}

	/**
	 * Determine if edit is enabled for given story reply based on configured edit
	 * expiry time.
	 *
	 * @param storyReply
	 * @return true if edit is enabled, false otherwise.
	 * @see {@link AppConfigService#getStoryReplyEditExpiryInMinutes()}
	 */
	public boolean isEditEnabled(StoryReplyView storyReply) {
		return TimeUtil.isBefore(storyReply.getCreatedTimestamp().toLocalDateTime(),
				getAppConfigService().getStoryReplyEditExpiryInMinutes(), TimeUnit.MINUTES);
	}
}
