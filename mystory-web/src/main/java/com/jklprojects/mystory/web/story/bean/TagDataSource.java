/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

import com.jklprojects.mystory.business.story.entity.view.TagView;
import com.jklprojects.mystory.business.story.service.api.TagService;
import com.jklprojects.mystory.common.story.TagStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-05-23
 * @version 2, 2017-01-23
 * @version 4, 2019-03-23
 */
@Named("tagDataSource")
@SessionScoped
public class TagDataSource implements Serializable {

	private static final long serialVersionUID = 1074575084879861789L;

	@EJB
	private TagService tagService;

	private Set<TagView> tags = new HashSet<>();

	@PostConstruct
	public void init() {
		tags = tagService.findTagsWithStatuses(TagStatus.ACTIVE);
	}

	public TagDataSource() {
		super();
	}

	public Set<TagView> getTags() {
		return tags;
	}

	public void setTags(Set<TagView> tags) {
		this.tags = tags;
	}

	public List<TagView> queryByName(final String name) {
		List<TagView> queried = new ArrayList<>();
		String nameLower = name.toLowerCase();

		for (TagView tag : getTags()) {
			String tagNameLower = tag.getName().toLowerCase();
			if (tagNameLower.startsWith(nameLower) || tagNameLower.contains(nameLower)) {
				queried.add(tag);
			}
		}

		return queried.parallelStream().sorted().collect(Collectors.toList());
	}
}
