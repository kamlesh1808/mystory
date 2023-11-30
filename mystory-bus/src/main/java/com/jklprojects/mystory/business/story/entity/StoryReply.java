/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, June 10, 2015
 */
@Entity
@Table(name = "A_STORY_REPLY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class StoryReply extends AbstractAppEntity implements Comparable<StoryReply> {

	private static final long serialVersionUID = 5880250681782029387L;

	@Column(name = "reply")
	private String reply;

	@ManyToOne
	@JoinColumn(name = "story_id", referencedColumnName = "id", nullable = false)
	private Story story;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_id")
	private StoryReply parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<StoryReply> replies = new ArrayList<>();

	@Column(name = "reply_num")
	private int replyNum;

	public StoryReply() {
		super();
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StoryReply getParent() {
		return parent;
	}

	public void setParent(StoryReply parent) {
		this.parent = parent;
	}

	public void addReply(StoryReply reply) {
		getReplies().add(reply);
	}

	public List<StoryReply> getReplies() {
		List<StoryReply> list = new ArrayList<>();
		for (StoryReply sr : replies.parallelStream().sorted().collect(Collectors.toList())) {
			if (sr.getParent() != null) {
				list.add(sr);
			}
		}
		return list;
	}

	public void setReplies(List<StoryReply> replies) {
		this.replies = replies;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof StoryReply))
			return false;

		StoryReply that = (StoryReply) o;

		return getParent() != null ? getParent().equals(that.getParent()) : that.getParent() == null;
	}

	@Override
	public int hashCode() {
		return getParent() != null ? getParent().hashCode() : 0;
	}

	/**
	 * Compare using created timestamp.
	 *
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(StoryReply o) {
		return getCreatedUpdated().getCreatedTimestamp().compareTo(o.getCreatedUpdated().getCreatedTimestamp());
	}
}
