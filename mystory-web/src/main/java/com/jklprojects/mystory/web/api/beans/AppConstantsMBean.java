/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.api.beans;

import com.jklprojects.mystory.common.story.StoryAccessType;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.StoryPostType;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.user.ContactUsStatus;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2019-03-23
 * @since 1, 2015-08-01
 */
@Named("appConstantsMBean")
@ApplicationScoped
public class AppConstantsMBean extends AbstractAppManagedBean {
	private static final long serialVersionUID = 5687944662667265424L;

	private static final XLogger logger = XLoggerFactory.getXLogger(AppConstantsMBean.class);

	private Collection<ContactUsStatus> contactUsStatuses;
	private Collection<StoryName> storyNames;
	private List<StoryName> storyNamesDisplay;
	private Collection<StoryAccessType> storyAccessTypes;
	private Collection<StoryPostType> storyPostTypes;
	private Collection<StoryStatus> storyStatuses;

	public Collection<StoryName> getStoryNames() {
		if (storyNames == null || storyNames.isEmpty()) {
			storyNames = Arrays.asList(StoryName.values());
		}
		return storyNames;
	}

	public Collection<StoryName> getStoryNamesDisplay() {
		if (storyNamesDisplay == null || storyNamesDisplay.isEmpty()) {
			storyNamesDisplay = Arrays.asList(StoryName.BITS_AND_BYTES, StoryName.EAT_WELL, StoryName.GRATITUDE_SHARED,
					StoryName.HEALTHY_BODY, StoryName.HEALTHY_MIND, StoryName.BE_HEALED, StoryName.LIFE_LOVED,
					StoryName.LINKED_IN, StoryName.UNIVERSAL_CONNECTION);
		}
		return storyNamesDisplay;
	}

	public Collection<StoryAccessType> getStoryAccessTypes() {
		if (storyAccessTypes == null || storyAccessTypes.isEmpty()) {
			storyAccessTypes = Arrays.asList(StoryAccessType.values());
		}
		return storyAccessTypes;
	}

	public Collection<StoryPostType> getStoryPostTypes() {
		if (storyPostTypes == null || storyPostTypes.isEmpty()) {
			storyPostTypes = Arrays.asList(StoryPostType.values());
		}
		return storyPostTypes;
	}

	public Collection<StoryStatus> getStoryStatuses() {
		if (storyStatuses == null || storyStatuses.isEmpty()) {
			storyStatuses = Arrays.asList(StoryStatus.values());
		}
		return storyStatuses;
	}

	public Collection<ContactUsStatus> getContactUsStatuses() {
		if (contactUsStatuses == null || contactUsStatuses.isEmpty()) {
			contactUsStatuses = Arrays.asList(ContactUsStatus.values());
		}
		return contactUsStatuses;
	}
}
