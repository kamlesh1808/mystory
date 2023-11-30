/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.story.entity.Story;
import com.jklprojects.mystory.business.story.entity.Tag;
import com.jklprojects.mystory.common.story.TagStatus;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-05-16
 * @version 2, 2018-03-23
 */
public class TagView extends AbstractAppView<Tag> implements Comparable<TagView> {
	private static final long serialVersionUID = -6356730582514028668L;

	private static final XLogger logger = XLoggerFactory.getXLogger(Story.class);

	private String name;
	private String desc;
	private TagStatus status;

	private Set<StoryView> stories = new HashSet<>();

	public TagView() {
		super(Tag.class);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public TagStatus getStatus() {
		return status;
	}

	public void setStatus(TagStatus status) {
		this.status = status;
	}

	public Set<StoryView> getStories() {
		return stories;
	}

	public void setStories(Set<StoryView> stories) {
		this.stories = stories;
	}

	@Override
	public Tag setEntityFromView(Tag entity, boolean setBi) {
		entity.setId(getId());

		entity.setName(getName());
		entity.setDesc(getDesc());
		entity.setStatus(getStatus());

		if (setBi) {
			Set<Story> stories = new HashSet<>();
			for (StoryView story : getStories()) {
				stories.add(story.setEntityFromView(new Story(), false));
			}
			entity.setStories(stories);
		}

		return entity;
	}

	/**
	 * Return the updated view based on the given entity.
	 *
	 * @param entity
	 * @return updated view
	 */
	@Override
	public TagView setViewFromEntity(Tag entity, boolean setBi) {
		super.setViewFromEntity(entity, setBi);

		setName(entity.getName());
		setDesc(entity.getDesc());
		setStatus(entity.getStatus());

		if (setBi) {
			for (Story story : entity.getStories()) {
				addStory(new StoryView().setViewFromEntity(story, false));
			}
		}

		return this;
	}

	public static Set<TagView> toViews(Collection<Tag> entities) {
		Set<TagView> views = new HashSet<>();
		for (Tag e : entities) {
			views.add(new TagView().setViewFromEntity(e, true));
		}
		return views;
	}

	/**
	 * Add story
	 *
	 * @param story
	 */
	public void addStory(StoryView story) {
		logger.entry(story);
		if (!stories.contains(story)) {
			logger.trace("addStory {}", story.toString());
			stories.add(story);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TagView))
			return false;

		TagView tagView = (TagView) o;

		return getName() != null ? getName().equals(tagView.getName()) : tagView.getName() == null;
	}

	@Override
	public int hashCode() {
		return getName() != null ? getName().hashCode() : 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TagView [getName()=").append(getName()).append(", toString()=").append(super.toString())
				.append("]");
		return builder.toString();
	}

	/**
	 * Compare using name.
	 *
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(TagView o) {
		return getName() != null && o != null ? getName().compareTo(o.getName()) : 0;
	}
}
