/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.story.entity.converter.StoryAccessTypeAttrConverter;
import com.jklprojects.mystory.business.story.entity.converter.StoryNameAttrConverter;
import com.jklprojects.mystory.business.story.entity.converter.StoryPostTypeAttrConverter;
import com.jklprojects.mystory.business.story.entity.converter.StoryStatusAttrConverter;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.common.story.StoryAccessType;
import com.jklprojects.mystory.common.story.StoryAuthor;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.StoryPostType;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.story.StoryTag;
import com.jklprojects.mystory.common.util.CollUtil;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Story entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-30
 */
@Entity
@Table(name = "A_STORY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
		@NamedQuery(name = "findStoriesAll", query = "SELECT s FROM Story s " + "INNER JOIN FETCH s.tags t "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesAllOrderByIdDesc", query = "SELECT s FROM Story s "
				+ "INNER JOIN FETCH s.tags t " + "ORDER BY s.id DESC"),
		@NamedQuery(name = "findStoryWithUserID", query = "SELECT s FROM Story s " + "INNER JOIN FETCH s.user u "
				+ "INNER JOIN FETCH s.tags t "
				+ "WHERE (u.userName = :userID OR u.email = :email) AND s.storyName = :storyName "
				+ "AND s.storyType = :storyType AND s.status IN :status "),
		@NamedQuery(name = "findStoryWithUserUID", query = "SELECT s FROM Story s " + "INNER JOIN FETCH s.user u "
				+ "INNER JOIN FETCH s.tags t " + "WHERE u.uid = :userUID AND s.storyName = :storyName "
				+ "AND s.storyType = :storyType AND s.status IN :status "),
		@NamedQuery(name = "findStoryEqualsUserOAuth2", query = "SELECT s FROM Story s "
				+ "INNER JOIN FETCH s.userOAuth2 uo " + "INNER JOIN FETCH s.tags t "
				+ "WHERE uo.idOAuth2 = :oAuth2UID AND s.storyName = :storyName "
				+ "AND s.storyType = :storyType AND s.status IN :status "),
		@NamedQuery(name = "findStoriesWithUserUID", query = "SELECT DISTINCT s FROM Story s "
				+ "INNER JOIN FETCH s.user u " + "INNER JOIN FETCH s.tags t "
				+ "WHERE u.uid = :userUID AND s.status IN :status "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesWithUserOAuthUID", query = "SELECT DISTINCT s FROM Story s "
				+ "INNER JOIN FETCH s.userOAuth2 ua " + "INNER JOIN FETCH s.tags t "
				+ "WHERE ua.idOAuth2 = :userOAuth2UID AND s.status IN :status "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesUserWithUserNameOrEmailStoryNames", query = "SELECT s FROM Story s "
				+ "INNER JOIN FETCH s.user u " + "INNER JOIN FETCH s.tags t "
				+ "WHERE (u.userName = :userID OR u.email = :email) "
				+ "AND s.status IN :status AND s.storyName IN :storyNames "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesUserWithUIDStoryNames", query = "SELECT s FROM Story s "
				+ "INNER JOIN FETCH s.user u " + "INNER JOIN FETCH s.tags t "
				+ "WHERE u.uid = :userUID AND s.status IN :status " + "AND s.storyName IN :storyNames "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesWithAccessType", query = "SELECT DISTINCT s FROM Story s "
				+ "INNER JOIN FETCH s.tags t " + "WHERE s.accessType IN :accessType AND s.status IN :status "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesWithAccessTypeWithUserFilter", query = "SELECT DISTINCT s FROM Story s "
				+ "INNER JOIN FETCH s.user u " + "INNER JOIN FETCH s.tags t "
				+ "WHERE s.status IN :status AND s.accessType IN :accessType " + "AND u.uid NOT IN :userUIDFilter "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesWithStatus", query = "SELECT DISTINCT s FROM Story s "
				+ "INNER JOIN FETCH s.tags t " + "WHERE s.status IN :status "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesWithStoryNames", query = "SELECT DISTINCT s FROM Story s "
				+ "INNER JOIN FETCH s.tags t " + "WHERE s.status IN :status AND s.storyName IN :storyNames "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesWithTags", query = "SELECT s FROM Story s " + "INNER JOIN FETCH s.tags t "
				+ "WHERE t.name IN :tags AND s.status IN :status "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesUserWithTags", query = "SELECT s FROM Story s " + "INNER JOIN FETCH s.tags t "
				+ "INNER JOIN FETCH s.user u " + "WHERE u.uid = :userUID AND s.status IN :status "
				+ "AND t.name IN :tags "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesUserOAuthWithTags", query = "SELECT s FROM Story s "
				+ "INNER JOIN FETCH s.tags t " + "INNER JOIN FETCH s.userOAuth2 ua "
				+ "WHERE ua.idOAuth2 = :userOAuth2UID AND s.status IN :status " + "AND t.name IN :tags "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesWithTagIds", query = "SELECT s FROM Story s " + "INNER JOIN FETCH s.tags t "
				+ "WHERE t.id IN :tagIds AND s.status IN :status "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findStoriesCreatedBetween", query = "SELECT s FROM Story s "
				+ "WHERE s.createdUpdated.createdTimestamp BETWEEN :startPeriod AND :endPeriod "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC"),
		@NamedQuery(name = "findStoriesByStoryNameAndTags", query = "SELECT s FROM Story s "
				+ "INNER JOIN FETCH s.tags t " + "WHERE t.name IN :tags AND s.status IN :status "
				+ "AND s.storyName = :storyName "
				+ "ORDER BY s.createdUpdated.createdTimestamp DESC, s.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "countStoriesWithUserUID", query = "SELECT COUNT(s) FROM Story s INNER JOIN s.user u "
				+ "WHERE u.uid = :userUID AND s.status IN :status"),
		@NamedQuery(name = "countStoriesWithUserOAuthUID", query = "SELECT COUNT(s) FROM Story s INNER JOIN s.userOAuth2 ua "
				+ "WHERE ua.idOAuth2 = :userOAuthUID AND s.status IN :status"),
		@NamedQuery(name = "countStoriesWithUserUIDStoryName", query = "SELECT COUNT(s) FROM Story s "
				+ "INNER JOIN s.user u "
				+ "WHERE u.uid = :userUID AND s.status IN :status AND s.storyName = :storyName"),
		@NamedQuery(name = "countStoriesWithUserOAuthUIDStoryName", query = "SELECT COUNT(s) FROM Story s "
				+ "INNER JOIN s.userOAuth2 ua "
				+ "WHERE ua.idOAuth2 = :userOAuthUID AND s.status IN :status AND s.storyName = :storyName")})
@NamedNativeQueries({
		@NamedNativeQuery(name = "findStoryAuthorsApp", query = "select u.uid as user_uid, u.status as user_status, u.first_name, "
				+ "u.last_name, u.user_role_type, count(s.id) as stories_count " + "from {h-schema}s_user u "
				+ "inner join {h-schema}a_story s on u.id = s.user_id " + "where u.status = 3 and s.status = 3 "
				+ "group by u.uid, u.status, u.first_name, u.last_name, u.user_role_type "
				+ "order by stories_count desc", resultSetMapping = "StoryAuthor"),
		@NamedNativeQuery(name = "findStoryAuthorsOAuth", query = "select ua.id_oauth2 as user_uid, ua.status as user_status, ua.first_name, "
				+ "ua.last_name, ua.user_role_type, count(s.id) as stories_count " + "from {h-schema}s_user_oauth2 ua "
				+ "inner join {h-schema}a_story s on ua.id = s.user_oauth2_id "
				+ "where ua.status = 3 and s.status = 3 "
				+ "group by ua.id_oauth2, ua.status, ua.first_name, ua.last_name, ua.user_role_type "
				+ "order by stories_count desc", resultSetMapping = "StoryAuthor"),
		@NamedNativeQuery(name = "findStoryTags", query = "select t.id as story_tag_id, t.name as story_tag_name, "
				+ "t.status as tag_status, count(t.id) as stories_count " + "from {h-schema}a_tag t "
				+ "inner join {h-schema}a_story_tag st on t.id = st.a_tag_id "
				+ "inner join {h-schema}a_story s on s.id = st.a_story_id " + "where s.status = 3 and t.status = 1"
				+ "group by t.id, t.name, t.status " + "order by stories_count desc", resultSetMapping = "StoryTag"),
		@NamedNativeQuery(name = "incrementStoryViews", query = "update {h-schema}a_story set views = views + 1 where id = :storyId"),})
@SqlResultSetMapping(name = "StoryAuthor", classes = {@ConstructorResult(targetClass = StoryAuthor.class, columns = {
		@ColumnResult(name = "user_uid", type = String.class),
		@ColumnResult(name = "user_status", type = Integer.class),
		@ColumnResult(name = "first_name", type = String.class), @ColumnResult(name = "last_name", type = String.class),
		@ColumnResult(name = "user_role_type", type = Integer.class),
		@ColumnResult(name = "stories_count", type = Integer.class)})})
@SqlResultSetMapping(name = "StoryTag", classes = {@ConstructorResult(targetClass = StoryTag.class, columns = {
		@ColumnResult(name = "story_tag_id", type = Long.class),
		@ColumnResult(name = "story_tag_name", type = String.class),
		@ColumnResult(name = "tag_status", type = Integer.class),
		@ColumnResult(name = "stories_count", type = Integer.class)})})
public class Story extends AbstractAppEntity {
	private static final long serialVersionUID = -854146953741391438L;

	private static final XLogger logger = XLoggerFactory.getXLogger(Story.class);

	@ManyToMany
	// cascade default is none, Tag entity is static - update is not desirable
	@JoinTable(name = "A_STORY_TAG", joinColumns = @JoinColumn(name = "a_story_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "a_tag_id", referencedColumnName = "id"))
	private final Set<Tag> tags = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "user_oauth2_id")
	private UserOAuth2 userOAuth2;

	@Column(name = "story_name")
	@Convert(converter = StoryNameAttrConverter.class)
	private StoryName storyName;

	@Column(name = "story_type")
	@Convert(converter = StoryPostTypeAttrConverter.class)
	private StoryPostType storyType;

	@Column(name = "access_type")
	@Convert(converter = StoryAccessTypeAttrConverter.class)
	private StoryAccessType accessType;

	@Column(name = "title")
	private String title;

	@Column(name = "story_text")
	private String storyText;

	@Column(name = "status")
	@Convert(converter = StoryStatusAttrConverter.class)
	private StoryStatus status;

	@Column(name = "views")
	private int views;

	@Column(name = "storytext_updated_timestamp")
	private OffsetDateTime storyTextUpdatedTimestamp;

	/** public no-arg constructor */
	public Story() {
		super();

		status = StoryStatus.NEW;
	}

	public User getUser() {
		return user;
	}

	/**
	 * Set user bi-directionally.
	 *
	 * @param user
	 */
	public void setUser(User user) {
		logger.entry(user);
		this.user = user;
	}

	public UserOAuth2 getUserOAuth2() {
		return userOAuth2;
	}

	public void setUserOAuth2(UserOAuth2 userOAuth2) {
		this.userOAuth2 = userOAuth2;
	}

	@Override
	public String toString() {
		return "Story{" + "storyName=" + storyName + ", storyType=" + storyType + ", accessType=" + accessType
				+ ", title='" + title + '\'' + ", status=" + status + "} " + super.toString();
	}

	/**
	 * @param user
	 * @return
	 */
	private boolean isUserEqualsGiven(User user) {
		return getUser() == null ? user == null : getUser().equals(user);
	}

	/**
	 * @param userOAuth2
	 * @return
	 */
	private boolean isUserOAuth2EqualsGiven(UserOAuth2 userOAuth2) {
		return getUserOAuth2() == null ? userOAuth2 == null : getUserOAuth2().equals(userOAuth2);
	}

	public StoryName getStoryName() {
		return storyName;
	}

	public void setStoryName(StoryName storyName) {
		this.storyName = storyName;
	}

	public StoryPostType getStoryType() {
		return storyType;
	}

	public void setStoryType(StoryPostType storyType) {
		this.storyType = storyType;
	}

	public StoryAccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(StoryAccessType accessType) {
		this.accessType = accessType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStoryText() {
		return storyText;
	}

	public void setStoryText(String storyText) {
		this.storyText = storyText;
	}

	public StoryStatus getStatus() {
		return status;
	}

	public void setStatus(StoryStatus status) {
		this.status = status;
	}

	public int getViews() {
		return views;
	}

	public OffsetDateTime getStoryTextUpdatedTimestamp() {
		return storyTextUpdatedTimestamp;
	}

	public void setStoryTextUpdatedTimestamp(OffsetDateTime storyTextUpdatedTimestamp) {
		this.storyTextUpdatedTimestamp = storyTextUpdatedTimestamp;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * Set tags.
	 *
	 * @param tags
	 */
	public void setTags(Set<Tag> tags) {
		if (CollUtil.isNotEmpty(tags)) {
			for (Tag tag : tags) {
				addTag(tag);
			}

			updateTags();
		} else {
			// remove all
			for (Iterator<Tag> tagItr = getTags().iterator(); tagItr.hasNext();) {
				Tag tag = tagItr.next();
				tagItr.remove();
				tag.getStories().remove(this);
			}
		}
	}

	private void updateTags() {
		// remove
		for (Iterator<Tag> tagItr = getTags().iterator(); tagItr.hasNext();) {
			boolean found = false;
			Tag tag = tagItr.next();
			for (Tag tagNew : tags) {
				if (tag.equals(tagNew)) {
					found = true;
					break;
				}
			}
			if (!found) {
				tagItr.remove();
				tag.getStories().remove(this);
			}
		}
	}

	/**
	 * Add tag bi-directionally.
	 *
	 * @param tag
	 */
	public void addTag(Tag tag) {
		// logger.entry(tag);
		if (!tags.contains(tag)) {
			// logger.trace("addTag {}", tag.toString());
			tags.add(tag);

			tag.addStory(this);
		}
	}

	/**
	 * Remove tag bi-directionally.
	 *
	 * @param tag
	 */
	public void removeTag(Tag tag) {
		// logger.entry(tag);
		if (tags.contains(tag)) {
			// logger.trace("removeTag {} ", tag.toString());
			tags.remove(tag);

			tag.removeStory(this);
		}
	}

	@Override
	public int hashCode() {
		int result = getStoryName().hashCode();
		result = 31 * result + getTitle().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Story story = (Story) o;

		if (getStoryName() != story.getStoryName())
			return false;
		return getTitle().equals(story.getTitle());
	}

	public List<StoryTag> getStoryTags() {
		List<StoryTag> storyTags = new ArrayList<>(tags.size());
		for (Tag tag : tags) {
			storyTags.add(new StoryTag(tag.getId(), tag.getName(), tag.getStatus()));
		}
		return storyTags;
	}
}
