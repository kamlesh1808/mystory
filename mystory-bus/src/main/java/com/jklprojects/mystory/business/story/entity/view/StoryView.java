/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.story.entity.Story;
import com.jklprojects.mystory.business.story.entity.Tag;
import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.common.story.StoryAccessType;
import com.jklprojects.mystory.common.story.StoryName;
import com.jklprojects.mystory.common.story.StoryPostType;
import com.jklprojects.mystory.common.story.StoryStatus;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.StringUtil;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.slf4j.profiler.Profiler;

/**
 * Represents light weight view for Story entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-30
 */
public class StoryView extends AbstractAppView<Story> {
	private static final long serialVersionUID = 8002071703640628L;

	private static final XLogger logger = XLoggerFactory.getXLogger(StoryView.class);

	private StoryName storyName;
	private StoryPostType storyType;
	private StoryAccessType accessType;
	private String title;
	private String storyText;
	private StoryStatus status;
	private int views;
	private OffsetDateTime storyTextUpdatedTimestamp;
	private int repliesCount = 0;

	private UserView user = new UserView();
	private UserOAuth2View userOAuth2 = new UserOAuth2View();

	private List<TagView> tags = new ArrayList<>();
	private List<StoryReplyView> storyReplies = new ArrayList<>();

	public StoryView() {
		super(Story.class);
	}

	public static List<StoryView> toViews(List<Story> stories) {
		Profiler profilerToViews = new Profiler("StoryView#toViews");
		profilerToViews.setLogger(logger);

		List<StoryView> storiesV = new ArrayList<>();
		for (Story storyE : stories) {
			storiesV.add(new StoryView().setViewFromEntity(storyE, true));
		}

		logger.exit(storiesV.size());
		profilerToViews.stop().log();

		return storiesV;
	}

	/**
	 * Add story reply.
	 *
	 * @param storyReply
	 */
	public void addStoryReply(StoryReplyView storyReply) {
		logger.entry(storyReply);
		if (!storyReplies.contains(storyReply) && storyReply.getParent() == null) {
			logger.trace("addStoryReply {}", storyReply.toString());
			storyReplies.add(storyReply);

			storyReply.setStory(this);
		}
	}

	/**
	 * Add story tag.
	 *
	 * @param tag
	 */
	public void addTag(TagView tag) {
		// logger.entry(tag);
		if (!tags.contains(tag)) {
			// logger.trace("addTag {}", tag.toString());
			tags.add(tag);
		}
	}

	/**
	 * Count and return all story replies and replies to reply.
	 *
	 * @return count of replies
	 */
	public int countReplies() {
		int count = storyReplies.size();
		for (StoryReplyView sr : storyReplies) {
			count += countRepliesRecursive(sr);
		}
		setRepliesCount(count);
		return count;
	}

	public StoryAccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(StoryAccessType accessType) {
		this.accessType = accessType;
	}

	/**
	 * Collect and sort all replies with "reply to reply" created date sort order.
	 *
	 * @return sortedReplies
	 */
	public List<StoryReplyView> getAllReplies() {
		List<StoryReplyView> sortedReplies = new ArrayList<>();
		Collections.sort(storyReplies);
		for (StoryReplyView sr : storyReplies) {
			addReplyRecursive(sr, sortedReplies);
		}
		return sortedReplies;
	}

	public int getRepliesCount() {
		return repliesCount;
	}

	public void setRepliesCount(int repliesCount) {
		this.repliesCount = repliesCount;
	}

	public StoryStatus getStatus() {
		return status;
	}

	public void setStatus(StoryStatus status) {
		this.status = status;
	}

	public StoryName getStoryName() {
		return storyName != null ? storyName : StoryName.UNDEFINED;
	}

	public void setStoryName(StoryName storyName) {
		this.storyName = storyName;
	}

	public int getStoryNameId() {
		return storyName != null ? storyName.getId() : StoryName.UNDEFINED.getId();
	}

	public List<StoryReplyView> getStoryReplies() {
		return storyReplies;
	}

	public void setStoryReplies(List<StoryReplyView> storyReplies) {
		this.storyReplies = storyReplies;
	}

	public String getStoryText() {
		return storyText;
	}

	public void setStoryText(String storyText) {
		this.storyText = storyText;
	}

	public OffsetDateTime getStoryTextUpdatedTimestamp() {
		return storyTextUpdatedTimestamp;
	}

	public void setStoryTextUpdatedTimestamp(OffsetDateTime storyTextUpdatedTimestamp) {
		this.storyTextUpdatedTimestamp = storyTextUpdatedTimestamp;
	}

	public String getStoryTextUpdatedTimestampFormatted() {
		return storyTextUpdatedTimestamp != null
				? DateTimeFormatter.ofPattern(SHORT_DATE).format(storyTextUpdatedTimestamp)
				: "";
	}

	public StoryPostType getStoryType() {
		return storyType;
	}

	public void setStoryType(StoryPostType storyType) {
		this.storyType = storyType;
	}

	public List<TagView> getTags() {
		Collections.sort(tags);
		return tags;
	}

	public void setTags(List<TagView> tags) {
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserView getUser() {
		return user;
	}

	public void setUser(UserView user) {
		this.user = user;
	}

	public UserOAuth2View getUserOAuth2() {
		return userOAuth2;
	}

	public void setUserOAuth2(UserOAuth2View userOAuth2) {
		this.userOAuth2 = userOAuth2;
	}

	public int getViews() {
		return views;
	}

	public boolean isStoryAuthorAppUser() {
		return getUser() != null && getUser().isIdSet();
	}

	public boolean isStoryAuthorOAuthUser() {
		return getUserOAuth2() != null && getUserOAuth2().isIdSet();
	}

	public String getAuthor() {
		return getAuthorFirstName() + " " + getAuthorLastName();
	}

	public String getAuthorFirstName() {
		return isStoryAuthorAppUser() ? getUser().getFirstName() : getUserOAuth2().getFirstName();
	}

	public String getAuthorLastName() {
		return isStoryAuthorAppUser() ? getUser().getLastName() : getUserOAuth2().getLastName();
	}

	public String getAuthorURL() {
		return (getAuthorFirstName() + "-" + getAuthorLastName()).toLowerCase();
	}

	public String getAuthorUserUID() {
		return isStoryAuthorAppUser() ? String.valueOf(getUser().getUid()) : getUserOAuth2().getIdOAuth2();
	}

	public long getAuthorUserId() {
		return isStoryAuthorAppUser() ? getUser().getId() : getUserOAuth2().getId();
	}

	public long getAuthorUserRoleTypeId() {
		return isStoryAuthorAppUser() ? getUser().getUserRoleType().getId() : getUserOAuth2().getUserRoleType().getId();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof StoryView))
			return false;

		StoryView storyView = (StoryView) o;

		if (getStoryName() != storyView.getStoryName())
			return false;
		if (getStoryType() != storyView.getStoryType())
			return false;
		if (getAccessType() != storyView.getAccessType())
			return false;
		return getTitle() != null ? getTitle().equals(storyView.getTitle()) : storyView.getTitle() == null;
	}

	@Override
	public int hashCode() {
		int result = getStoryName() != null ? getStoryName().hashCode() : 0;
		result = 31 * result + (getStoryType() != null ? getStoryType().hashCode() : 0);
		result = 31 * result + (getAccessType() != null ? getAccessType().hashCode() : 0);
		result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
		return result;
	}

	public boolean isActive() {
		return StoryStatus.ACTIVE == getStatus();
	}

	public boolean isInActive() {
		return StoryStatus.INACTIVE == getStatus();
	}

	public void removeTag(TagView tag) {
		logger.entry(tag);
		if (tags.contains(tag)) {
			logger.trace("removeTag {}", tag.toString());
			tags.remove(tag);
		}
	}

	@Override
	public Story setEntityFromView(Story entity, boolean setBi) {
		logger.entry(this);

		if (getStatus() != null) {
			entity.setStatus(getStatus());
		}

		entity.setStoryName(getStoryName());
		entity.setStoryType(getStoryType());
		entity.setAccessType(getAccessType());

		entity.setStatus(getStatus());
		entity.setTitle(getTitle());

		if (setBi) {
			if (CollUtil.isNotEmpty(getTags())) {
				Set<Tag> tagsNew = new HashSet<>();
				for (TagView tag : getTags()) {
					tagsNew.add(tag.setEntityFromView(new Tag(), false));
				}
				entity.setTags(tagsNew);
			} else {
				entity.setTags(Collections.emptySet());
			}
		}

		if (getStoryText() != null && !getStoryText().equals(entity.getStoryText())) {
			if (!StringUtil.isEmpty(entity.getStoryText())) {
				entity.setStoryTextUpdatedTimestamp(OffsetDateTime.now());
			}
			entity.setStoryText(getStoryText());
		}

		return entity;
	}

	@Override
	public StoryView setViewFromEntity(Story entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		logger.entry(entity);

		setStatus(entity.getStatus());

		setStoryName(entity.getStoryName());
		setStoryType(entity.getStoryType());
		setAccessType(entity.getAccessType());
		setStoryText(entity.getStoryText());
		setStatus(entity.getStatus());
		setTitle(entity.getTitle());
		views = entity.getViews();
		setStoryTextUpdatedTimestamp(entity.getStoryTextUpdatedTimestamp());

		if (setBi) {
			// set storyTags
			for (Tag tag : entity.getTags()) {
				addTag(new TagView().setViewFromEntity(tag, false));
			}

			if (entity.getUser() != null) {
				setUser(new UserView().setViewFromEntity(entity.getUser(), false));
			}

			if (entity.getUserOAuth2() != null) {
				setUserOAuth2(new UserOAuth2View().setViewFromEntity(entity.getUserOAuth2(), false));
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return "StoryView{" + "storyName=" + storyName + ", storyType=" + storyType + ", accessType=" + accessType
				+ ", title='" + title + '\'' + ", status=" + status + ", views=" + views + "} " + super.toString();
	}

	private void addReplyRecursive(StoryReplyView sr, List<StoryReplyView> list) {
		list.add(sr);
		Collections.sort(sr.getReplies());
		for (StoryReplyView child : sr.getReplies()) {
			addReplyRecursive(child, list);
		}
	}

	private int countRepliesRecursive(StoryReplyView sr) {
		int count = sr.getReplies().size();
		for (StoryReplyView child : sr.getReplies()) {
			count += countRepliesRecursive(child);
		}
		return count;
	}
}
