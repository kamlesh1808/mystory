/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.bean;

import com.jklprojects.mystory.business.story.entity.view.TagView;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-05-23
 * @version 2, 2017-01-23
 * @version 4, 2019-03-23
 */
@Named
@RequestScoped
public class TagsACMBean {
	@Inject
	private TagDataSource tagDS;

	private List<TagView> tags;

	public TagsACMBean() {
		super();
	}

	public List<TagView> complete(String query) {
		return tagDS.queryByName(query);
	}

	public List<TagView> getTags() {
		return tags;
	}

	public void setTags(List<TagView> tags) {
		this.tags = tags;
	}

	public TagDataSource getTagDS() {
		return tagDS;
	}

	public void setTagDS(TagDataSource tagDS) {
		this.tagDS = tagDS;
	}
}
