/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.story.entity.StoryReply;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.common.ex.AppCodeException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, June 11, 2015
 */
public class StoryReplyView extends AbstractAppView<StoryReply> implements Comparable<StoryReplyView> {

	private static final long serialVersionUID = -4072970861262733599L;

	private String reply;
	private StoryView story;
	private UserView user;
	private StoryReplyView parent;
	private List<StoryReplyView> replies;
	private int replyNum;

	public StoryReplyView() {
		super(StoryReply.class);
	}

	@Override
	public StoryReply setEntityFromView(StoryReply entity, boolean setBi) throws AppCodeException {
		entity.setId(getId());

		entity.setReply(getReply());
		entity.setReplyNum(getReplyNum());

		return entity;
	}

	/**
	 * Return the updated view based on the given entity.
	 *
	 * @param entity
	 * @return updated view
	 */
	@Override
	public StoryReplyView setViewFromEntity(StoryReply entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		setReply(entity.getReply());
		if (setBi) {
			setUser(new UserView().setViewFromEntity(entity.getUser(), false));
			setStory(new StoryView().setViewFromEntity(entity.getStory(), false));

			List<StoryReplyView> repliesV = new ArrayList<>();
			for (StoryReply sr : entity.getReplies()) {
				if (sr.getParent() != null) {
					StoryReplyView child = new StoryReplyView().setViewFromEntity(sr, true);
					child.setParent(this);
					repliesV.add(child);
				}
			}
			setReplies(repliesV);
			setReplyNum(entity.getReplyNum());
		}

		return this;
	}

	public static List<StoryReplyView> toViews(Collection<StoryReply> entities) {
		List<StoryReplyView> views = new ArrayList<>();
		for (StoryReply e : entities) {
			views.add(new StoryReplyView().setViewFromEntity(e, true));
		}
		return views;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public StoryView getStory() {
		return story;
	}

	public void setStory(StoryView story) {
		this.story = story;
	}

	public UserView getUser() {
		return user;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StoryReplyView [getReply()=").append(getReply()).append(", toString()=")
				.append(super.toString()).append("]");
		return builder.toString();
	}

	public void setUser(UserView user) {
		this.user = user;
	}

	public StoryReplyView getParent() {
		return parent;
	}

	public void setParent(StoryReplyView parent) {
		this.parent = parent;
	}

	public List<StoryReplyView> getReplies() {
		return replies;
	}

	public void setReplies(List<StoryReplyView> replies) {
		this.replies = replies;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof StoryReplyView))
			return false;

		StoryReplyView that = (StoryReplyView) o;

		if (getReplyNum() != that.getReplyNum())
			return false;
		return getParent() != null ? getParent().equals(that.getParent()) : that.getParent() == null;
	}

	@Override
	public int hashCode() {
		int result = getParent() != null ? getParent().hashCode() : 0;
		result = 31 * result + getReplyNum();
		return result;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	/**
	 * Compare using created timestamp.
	 *
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(StoryReplyView o) {
		return getCreatedTimestamp().compareTo(o.getCreatedTimestamp());
	}
}
