/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

import com.jklprojects.mystory.common.story.StoryAccessType;
import com.jklprojects.mystory.common.story.StoryAuthor;
import com.jklprojects.mystory.common.story.StoryTag;
import com.jklprojects.mystory.common.story.dto.StoryLinkDTO;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
@Named("storiesSummaryMBean")
@SessionScoped
public class StoriesSummaryMBean extends AbstractAppManagedBean {

	private static final XLogger logger = XLoggerFactory.getXLogger(StoriesSummaryMBean.class);

	private List<StoryLinkDTO> storyLinkDTOsWithAccessTypePublic = new ArrayList<>();
	private List<StoryLinkDTO> storyLinkDTOsSharedWithStoryName = new ArrayList<>();

	private List<StoryAuthor> storyAuthors = new ArrayList<>();
	private List<StoryTag> storyTags = new ArrayList<>();

	@PostConstruct
	public void init() {
		logger.entry();

		Profiler profiler = new Profiler("StoriesSummaryMBean#init");
		profiler.setLogger(logger);

		storyAuthors = getStoryService().findStoryAuthors();
		storyTags = getStoryService().findStoryTags();

		List<StoryLinkDTO> storyLinkDTOsWithAccessTypeAll = getStoryService()
				.findStoryLinksWithAccessType(StoryAccessType.PUBLIC, StoryAccessType.SHARE_WITH_STORY_NAME);

		storyLinkDTOsWithAccessTypePublic = storyLinkDTOsWithAccessTypeAll.stream()
				.filter(s -> s.getAccessType() == StoryAccessType.PUBLIC).collect(Collectors.toList());

		storyLinkDTOsSharedWithStoryName = storyLinkDTOsWithAccessTypeAll.stream()
				.filter(s -> s.getAccessType() == StoryAccessType.SHARE_WITH_STORY_NAME).collect(Collectors.toList());

		profiler.stop().log();
	}

	public List<StoryAuthor> getStoryAuthors() {
		return storyAuthors;
	}

	public List<StoryTag> getStoryTags() {
		return storyTags;
	}

	public List<StoryLinkDTO> getStoryLinkDTOsWithAccessTypePublic() {
		return storyLinkDTOsWithAccessTypePublic;
	}

	public List<StoryLinkDTO> getStoryLinkDTOsSharedWithStoryName() {
		return storyLinkDTOsSharedWithStoryName;
	}

	public boolean renderStoryLinkDTOsWithAccessTypePublic() {
		return CollUtil.isNotEmpty(storyLinkDTOsWithAccessTypePublic);
	}

	public boolean renderStoryLinkDTOsSharedWithStoryName() {
		return CollUtil.isNotEmpty(storyLinkDTOsSharedWithStoryName);
	}
}
