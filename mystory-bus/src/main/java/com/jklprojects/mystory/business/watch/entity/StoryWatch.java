/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.story.entity.Story;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.common.user.UserRoleType;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-07-15
 */
@Entity
@Table(name = "A_STORY_WATCH")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
		@NamedQuery(name = "countStoryWatchExistsWithUser", query = "SELECT COUNT(sw) FROM StoryWatch sw "
				+ "WHERE sw.storyId = : storyId AND (sw.userId = :userId OR sw.userOAuthId = :userId) "
				+ "AND sw.clientIpAddress = :clientIpAddress AND sw.viewDate = CURRENT_DATE "),
		@NamedQuery(name = "findStoryWatchBetween", query = "SELECT sw FROM StoryWatch sw "
				+ "INNER JOIN FETCH sw.story s "
				+ "WHERE sw.createdUpdated.createdTimestamp BETWEEN :startPeriod AND :endPeriod "
				+ "ORDER BY sw.createdUpdated.createdTimestamp DESC"),
		@NamedQuery(name = "findStoryWatchWithStory", query = "SELECT sw FROM StoryWatch sw "
				+ "WHERE sw.storyId = :storyId "
				+ "ORDER BY sw.createdUpdated.createdTimestamp DESC, sw.createdUpdated.updatedTimestamp DESC")})
public class StoryWatch extends AbstractAppEntity implements Comparable<StoryWatch> {

	private static final long serialVersionUID = 2647378394119347776L;

	private static final XLogger logger = XLoggerFactory.getXLogger(StoryWatch.class);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "story_id", insertable = false, updatable = false)
	private Story story;

	@Column(name = "story_id", nullable = false)
	private Long storyId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_oauth2_id", insertable = false, updatable = false)
	private UserOAuth2 userOAuth2;

	@Column(name = "user_oauth2_id", nullable = false)
	private Long userOAuthId;

	@Column(name = "client_ipaddress")
	private String clientIpAddress;

	@Column(name = "view_date")
	private LocalDate viewDate;

	/** no-arg constructor */
	public StoryWatch() {
		super();
	}

	/**
	 * Used to create Story Watch.
	 *
	 * @param storyId
	 * @param userId
	 * @param clientIpAddress
	 * @param viewDate
	 */
	public StoryWatch(Long storyId, Long userId, UserRoleType signedInUserRoleType, String clientIpAddress,
			LocalDate viewDate) {
		this();

		this.clientIpAddress = clientIpAddress;
		this.viewDate = viewDate;
		this.storyId = storyId;

		if (signedInUserRoleType.getUserType().isAppInternal()) {
			this.userId = userId;
			this.userOAuthId = null;
		} else if (signedInUserRoleType.getUserType().isOAuth2External()) {
			this.userOAuthId = userId;
			this.userId = null;
		}
	}

	/**
	 * Compare using created timestamp.
	 *
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(StoryWatch o) {
		return getCreatedUpdated().getCreatedTimestamp().compareTo(o.getCreatedUpdated().getCreatedTimestamp());
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
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

	public UserOAuth2 getUserOAuth2() {
		return userOAuth2;
	}

	public void setUserOAuth2(UserOAuth2 userOAuth2) {
		this.userOAuth2 = userOAuth2;
	}

	public LocalDate getViewDate() {
		return viewDate;
	}

	public void setViewDate(LocalDate viewDate) {
		this.viewDate = viewDate;
	}

	@Override
	public int hashCode() {
		int result = getClientIpAddress() != null ? getClientIpAddress().hashCode() : 0;
		result = 31 * result + (getViewDate() != null ? getViewDate().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		StoryWatch that = (StoryWatch) o;

		if (getClientIpAddress() != null
				? !getClientIpAddress().equals(that.getClientIpAddress())
				: that.getClientIpAddress() != null)
			return false;
		return getViewDate() != null ? getViewDate().equals(that.getViewDate()) : that.getViewDate() == null;
	}

	@Override
	public String toString() {
		return "StoryWatch{" + "clientIpAddress='" + clientIpAddress + '\'' + ", viewDate=" + viewDate + "} "
				+ super.toString();
	}
}
