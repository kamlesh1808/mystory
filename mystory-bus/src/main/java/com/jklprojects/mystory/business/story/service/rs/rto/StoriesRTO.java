/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.service.rs.rto;

import com.jklprojects.mystory.business.api.rs.rto.LinkRTO;
import com.jklprojects.mystory.business.common.rs.AbstractRTO;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.business.story.entity.view.TagView;
import com.jklprojects.mystory.business.user.service.rs.rto.UsersRTO;
import com.jklprojects.mystory.common.story.StoryAccessType;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.StoryPostType;
import com.jklprojects.mystory.common.story.StoryStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.HttpMethod;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Story immutable RTO.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-04-23
 * @version 2, 2018-04-23
 * @version 3, 2018-10-23
 * @version 3, 2018-11-23
 */
@XmlRootElement(name = "Story")
public class StoriesRTO extends AbstractRTO {
	private static final long serialVersionUID = -1931625813528240318L;

	public static final String RELATIONSHIP_TYPE = "stories";

	private long id;
	private StoryName storyName;
	private StoryPostType storyType;
	private StoryAccessType accessType;
	private String title;
	private StoryStatus status;
	private int views;
	private final int repliesCount = 0;
	private String createdTimestamp;
	private String updatedTimestamp;
	private String storyText;

	private final List<LinkRTO> links = new ArrayList<LinkRTO>();

	/** public no-arg constructor */
	public StoriesRTO() {
		super();
	}

	public StoriesRTO(String restAPIBaseURI) {
		super(restAPIBaseURI);
	}

	/**
	 * @param story
	 * @param restAPIBaseURI
	 */
	public StoriesRTO(StoryView story, String restAPIBaseURI) {
		this(restAPIBaseURI);

		if (story != null) {
			id = story.getId();
			storyName = story.getStoryName();
			storyType = story.getStoryType();
			accessType = story.getAccessType();
			title = story.getTitle();
			storyText = story.getStoryText();
			status = story.getStatus();
			views = story.getViews();

			createdTimestamp = story.getCreatedTimestampFormattedISOLocalDateTime();
			updatedTimestamp = story.getUpdatedTimestampFormattedISOLocalDateTime();

			for (TagView tagView : story.getTags()) {
				String href = new StringBuilder(getRestAPIBaseURI()).append(TagsRTO.RELATIONSHIP_TYPE).append("/")
						.append(tagView.getName()).toString();
				links.add(new LinkRTO(tagView.getName(), href, TagsRTO.RELATIONSHIP_TYPE, HttpMethod.GET));
			}

			String hrefUser = new StringBuilder(getRestAPIBaseURI()).append(UsersRTO.RELATIONSHIP_TYPE).append("/")
					.append(story.getUser().getUserName()).toString();
			links.add(new LinkRTO(story.getUser().getUserName(), hrefUser, UsersRTO.RELATIONSHIP_TYPE, HttpMethod.GET));
			Collections.sort(links);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof StoriesRTO))
			return false;

		StoriesRTO that = (StoriesRTO) o;

		if (getId() != that.getId())
			return false;
		if (getStoryName() != that.getStoryName())
			return false;
		if (getStoryType() != that.getStoryType())
			return false;
		return getTitle() != null ? getTitle().equals(that.getTitle()) : that.getTitle() == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (getId() ^ (getId() >>> 32));
		result = 31 * result + (getStoryName() != null ? getStoryName().hashCode() : 0);
		result = 31 * result + (getStoryType() != null ? getStoryType().hashCode() : 0);
		result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
		return result;
	}

	/** @return the accessType */
	public StoryAccessType getAccessType() {
		return accessType;
	}

	/** @return the createdTimestamp */
	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public long getId() {
		return id;
	}

	public List<LinkRTO> getLinks() {
		return links;
	}

	/** @return the repliesCount */
	public int getRepliesCount() {
		return repliesCount;
	}

	/** @return the status */
	public StoryStatus getStatus() {
		return status;
	}

	/** @return the storyName */
	public StoryName getStoryName() {
		return storyName;
	}

	/** @return the storyText */
	public String getStoryText() {
		return storyText;
	}

	/** @return the storyType */
	public StoryPostType getStoryType() {
		return storyType;
	}

	/** @return the title */
	public String getTitle() {
		return title;
	}

	/** @return the updatedTimestamp */
	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	/** @return the views */
	public int getViews() {
		return views;
	}

	@Override
	public String toString() {
		return "StoryRTO [getAccessType()=" + getAccessType() + ", getCreatedTimestamp()=" + getCreatedTimestamp()
				+ ", getRepliesCount()=" + getRepliesCount() + ", getStatus()=" + getStatus() + ", getStoryName()="
				+ getStoryName() + ", getStoryText()=" + getStoryText() + ", getStoryType()=" + getStoryType()
				+ ", getTitle()=" + getTitle() + ", getUpdatedTimestamp()=" + getUpdatedTimestamp() + ", getViews()="
				+ getViews() + "]";
	}
}
