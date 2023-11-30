/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.story.entity.converter.TagStatusAttrConverter;
import com.jklprojects.mystory.common.story.TagStatus;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, May 16, 2015
 */
@Entity
@Table(name = "A_TAG")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
		@NamedQuery(name = "findTagsWithStatuses", query = "SELECT t FROM Tag t WHERE t.status IN :status ORDER BY t.createdUpdated.createdTimestamp ASC"),
		@NamedQuery(name = "findTagWithName", query = "SELECT t FROM Tag t WHERE t.name = :tagName ORDER BY t.createdUpdated.createdTimestamp ASC"),
		@NamedQuery(name = "findTagsWithNames", query = "SELECT t FROM Tag t WHERE t.name IN :tags ORDER BY t.createdUpdated.createdTimestamp ASC")})
public class Tag extends AbstractAppEntity implements Comparable<Tag> {
	private static final long serialVersionUID = 6369125546506304889L;

	private static final XLogger logger = XLoggerFactory.getXLogger(Story.class);

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String desc;

	@Column(name = "status")
	@Convert(converter = TagStatusAttrConverter.class)
	private TagStatus status;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "A_STORY_TAG", joinColumns = @JoinColumn(name = "a_tag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "a_story_id", referencedColumnName = "id"))
	private Set<Story> stories = new HashSet<>();

	public Tag() {
		super();
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

	public Set<Story> getStories() {
		return stories;
	}

	public void setStories(Set<Story> stories) {
		this.stories = stories;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		com.jklprojects.mystory.business.story.entity.Tag tag = (com.jklprojects.mystory.business.story.entity.Tag) o;

		return getName().equals(tag.getName());
	}

	/**
	 * Add story bi-directionally.
	 *
	 * @param story
	 */
	public void addStory(Story story) {
		logger.entry(story);
		if (!stories.contains(story)) {
			logger.trace("addStory {}", story.toString());
			stories.add(story);

			story.addTag(this);
		}
	}

	@Override
	public String toString() {
		return "Tag{" + "name='" + name + '\'' + "} " + super.toString();
	}

	/**
	 * Remove story bi-directionally.
	 *
	 * @param tag
	 */
	public void removeStory(Story story) {
		logger.entry(story);
		if (stories.contains(story)) {
			logger.trace("removeStory {} ", story.toString());
			stories.remove(story);

			story.removeTag(this);
		}
	}

	/**
	 * Compare using name.
	 *
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(Tag o) {
		return getName() != null && o != null ? getName().compareTo(o.getName()) : 0;
	}
}
