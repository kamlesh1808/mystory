/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.story.StoryExceptions;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.dto.StoryLinkDTO;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.slf4j.profiler.Profiler;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
@Named("storyNameStoriesMBean")
@RequestScoped
public class StoryNameStoriesMBean extends AbstractAppManagedBean {

	private static final XLogger logger = XLoggerFactory.getXLogger(StoryNameStoriesMBean.class);

	private List<StoryLinkDTO> storyNameStoryLinkDTOs = new ArrayList<>();
	private StoryName storyName = null;

	@PostConstruct
	public void init() {
		logger.entry();

		Profiler profiler = new Profiler("StoryNameStoriesMBean#init");
		profiler.setLogger(logger);

		String storyNameId = getRequestParamStoryNameId();
		try {
			storyName = StoryName.toEnum(storyNameId);
			storyNameStoryLinkDTOs = getStoryService().findStoryLinkDTOsWithStoryNames(storyName);
		} catch (NoSuchElementException | NumberFormatException e) {
			processAppCodeException(StoryExceptions.STORY_003.newAppCodeException(storyNameId));
		} catch (AppCodeException e) {
			logger.warn(e.getI18NMessage());
			processAppCodeException(e);
		}

		profiler.stop().log();
	}

	public List<StoryLinkDTO> getStoryNameStoryLinkDTOs() {
		return storyNameStoryLinkDTOs;
	}

	public boolean renderStoryNameStories() {
		return CollUtil.isNotEmpty(storyNameStoryLinkDTOs);
	}

	public String getStoryName() {
		return storyName.getI18N();
	}
}
