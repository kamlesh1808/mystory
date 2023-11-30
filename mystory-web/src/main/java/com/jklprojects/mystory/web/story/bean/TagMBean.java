/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.story.Tag;
import com.jklprojects.mystory.common.story.dto.StoryLinkDTO;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.StringUtil;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.slf4j.profiler.Profiler;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
@Named("tagMBean")
@SessionScoped
public class TagMBean extends AbstractAppManagedBean {

	private static final XLogger logger = XLoggerFactory.getXLogger(TagMBean.class);

	private List<StoryLinkDTO> storyLinkDTOsWithSelectedTag = new ArrayList<>();
	private List<StoryLinkDTO> storyLinkDTOsWithTagSupport = new ArrayList<>();
	private List<StoryLinkDTO> storyLinkDTOsWithTagIntroduction = new ArrayList<>();

	@PostConstruct
	public void init() {
		logger.entry();
		Profiler profiler = new Profiler("TagBean#init");
		profiler.setLogger(logger);

		String tagIdStr = "";

		try {
			tagIdStr = getRequestParamTagId();
			if (StringUtil.isNotEmpty(tagIdStr)) {

				storyLinkDTOsWithSelectedTag = getStoryService().findStoryLinkDTOsWithTagIds(Long.parseLong(tagIdStr));
			}

			storyLinkDTOsWithTagIntroduction = getStoryService().findStoryLinkDTOsWithTags(Tag.INTRODUCTION.getName());
			storyLinkDTOsWithTagSupport = getStoryService().findStoryLinkDTOsWithTags(Tag.SUPPORT.getName());

		} catch (NumberFormatException e) {
			processAppException(new AppException(I18N.APP.getI18N("storiesByTags_tagIdInvalid", tagIdStr)));
		} catch (AppCodeException e) {
			logger.warn(e.getI18NMessage());
			processAppException(e);
		}

		profiler.stop().log();
	}

	public List<StoryLinkDTO> getStoryLinkDTOsWithSelectedTag() {
		return storyLinkDTOsWithSelectedTag;
	}

	public boolean renderStoryLinkDTOsWithSelectedTag() {
		return CollUtil.isNotEmpty(storyLinkDTOsWithSelectedTag);
	}

	public List<StoryLinkDTO> getStoryLinkDTOsWithTagSupport() {
		return storyLinkDTOsWithTagSupport;
	}

	public boolean renderStoryLinkDTOsWithTagSupport() {
		return CollUtil.isNotEmpty(storyLinkDTOsWithTagSupport);
	}

	public List<StoryLinkDTO> getStoryLinkDTOsWithTagIntroduction() {
		return storyLinkDTOsWithTagIntroduction;
	}

	public boolean renderStoryLinkDTOsWithTagIntroduction() {
		return CollUtil.isNotEmpty(storyLinkDTOsWithTagIntroduction);
	}
}
