/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.story.StoryAccessType;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.StoryPostType;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.util.StringUtil;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-29
 */
@Named("addStoryMBean")
@RequestScoped
public class AddStoryMBean extends AbstractAppManagedBean {
	private static final long serialVersionUID = -6276387397984949451L;

	private static final XLogger logger = XLoggerFactory.getXLogger(AddStoryMBean.class);

	private StoryView story;

	public static final String STORY_TITLE_REGEX = "[A-Za-z0-9s\\s]+";

	public AddStoryMBean() {
		super();
	}

	@PostConstruct
	public void init() {
		// create story view instance for UI binding usage
		story = new StoryView();
	}

	public void validateStoryName(FacesContext fc, UIComponent comp, Object value) throws ValidatorException {

		StoryName storyName = (StoryName) value;
		if (storyName == StoryName.UNDEFINED) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, I18N.APP.getI18N("share_storyNameRequired"), ""));
		}
	}

	public void validateStoryPostType(FacesContext fc, UIComponent comp, Object value) throws ValidatorException {

		StoryPostType storyType = (StoryPostType) value;
		if (storyType == StoryPostType.UNDEFINED) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, I18N.APP.getI18N("share_storyTypeRequired"), ""));
		}
	}

	/**
	 * Validate story title. Only
	 *
	 * @param fc
	 * @param comp
	 * @param value
	 * @throws ValidatorException
	 * @since 4.20.05.23
	 */
	public void validateStoryTitle(FacesContext fc, UIComponent comp, Object value) throws ValidatorException {
		String storyTitle = (String) value;
		if (!storyTitle.matches(STORY_TITLE_REGEX)) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, I18N.APP.getI18N("share_storyTitleInvalid"), ""));
		}
	}

	public void validateStoryText(FacesContext fc, UIComponent comp, Object value) throws ValidatorException {

		if (StringUtil.isEmpty((String) value)) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, I18N.APP.getI18N("share_storyTextRequired"), ""));
		}
	}

	public void validateAccessType(FacesContext fc, UIComponent comp, Object value) throws ValidatorException {

		StoryAccessType storyAccessType = (StoryAccessType) value;
		if (storyAccessType == StoryAccessType.UNDEFINED) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					I18N.APP.getI18N("share_storyAccessTypeRequired"), ""));
		}
	}

	/**
	 * Create story.
	 *
	 * @return navOutcome
	 */
	public String doAddStory() {
		String navOutcome = JSFNavigation.SUCCESS.getNavigation();
		StoryView storyCreated = null;

		try {
			story.setStatus(StoryStatus.NEW);
			String storyTitle = getStoryTitle(story);

			if (isSignedInUserTypeAppInternal()) {
				storyCreated = getStoryService().createStoryWithUserUID(story, getSignedInUserUIDLong());
			} else if (isSignedInUserTypeOAuth2External()) {
				storyCreated = getStoryService().createStoryWithUserOAuth2(story, getSignedInUserUID(),
						getSignedInUserRoleType());
			}

			if (storyCreated != null && storyCreated.isIdSet()) {
				addMsg(I18N.APP.getI18N("share_storyCreatedSuccessMsg", storyTitle), "", FacesMessage.SEVERITY_INFO);
				navOutcome = JSFNavigation.SUCCESS.getNavigation();

				logger.info("Story created: {}", storyCreated.toString());
			}

		} catch (AppCodeException e) {
			processAppCodeException(e);
			navOutcome = JSFNavigation.FAILURE.getNavigation();
		}
		return navOutcome;
	}

	public Collection<StoryName> getStoryNames() {
		return Arrays.asList(StoryName.values());
	}

	public Collection<StoryAccessType> getAccessTypes() {
		return Arrays.asList(StoryAccessType.values());
	}

	public Collection<StoryPostType> getStoryTypes() {
		return Arrays.asList(StoryPostType.values());
	}

	public StoryView getStory() {
		return story;
	}

	public void setStory(StoryView story) {
		this.story = story;
	}

	public Collection<StoryStatus> getStoryStatuses() {
		return Arrays.asList(StoryStatus.values());
	}
}
