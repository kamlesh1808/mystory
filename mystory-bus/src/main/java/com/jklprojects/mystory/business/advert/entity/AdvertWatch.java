/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.story.entity.Story;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.common.advert.AdvertWatchType;
import com.jklprojects.mystory.common.user.UserRoleType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2017-01-23
 */
@Entity
@Table(name = "F_ADVERT_WATCH")
@NamedQuery(name = "findAdvertWatchAll", query = "SELECT aw FROM AdvertWatch aw order by aw.createdUpdated.createdTimestamp DESC")
public class AdvertWatch extends AbstractAppEntity {

	private static final long serialVersionUID = -7435706159510594386L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "f_advert_id", insertable = false, updatable = false)
	private Advert advert;

	@Column(name = "f_advert_id")
	private Long advertId;

	@Column(name = "advert_watch_type")
	private AdvertWatchType advertWatchType;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "story_id", insertable = false, updatable = false)
	private Story story;

	@Column(name = "story_id")
	private Long storyId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@Column(name = "user_id")
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_oauth2_id", insertable = false, updatable = false)
	private UserOAuth2 userOAuth2;

	@Column(name = "user_oauth2_id")
	private Long userOAuthId;

	@Column(name = "watcher_ipaddress")
	private String watcherIpAddress;

	/** default no-arg constructor */
	public AdvertWatch() {
		super();
	}

	/**
	 * @param advertId
	 * @param advertWatchType
	 * @param storyId
	 * @param watcherUserId
	 * @param watcherRoleType
	 * @param watcherIpAddress
	 */
	public AdvertWatch(Long advertId, AdvertWatchType advertWatchType, Long storyId, Long watcherUserId,
			UserRoleType watcherRoleType, String watcherIpAddress) {

		this();

		this.advertId = advertId;
		this.advertWatchType = advertWatchType;
		this.storyId = storyId;
		this.watcherIpAddress = watcherIpAddress;

		if (watcherRoleType.getUserType().isAppInternal()) {
			this.userId = watcherUserId;
			this.userOAuthId = null;
		} else if (watcherRoleType.getUserType().isOAuth2External()) {
			this.userOAuthId = watcherUserId;
			this.userId = null;
		}
	}

	public Advert getAdvert() {
		return this.advert;
	}

	public void setAdvert(Advert advert) {
		this.advert = advert;
	}

	public AdvertWatchType getAdvertWatchType() {
		return advertWatchType;
	}

	public void setAdvertWatchType(AdvertWatchType advertWatchType) {
		this.advertWatchType = advertWatchType;
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

	public String getWatcherIpAddress() {
		return watcherIpAddress;
	}

	public void setWatcherIpAddress(String watcherIpAddress) {
		this.watcherIpAddress = watcherIpAddress;
	}

	@Override
	public int hashCode() {
		int result = getAdvertWatchType() != null ? getAdvertWatchType().hashCode() : 0;
		result = 31 * result + (getWatcherIpAddress() != null ? getWatcherIpAddress().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AdvertWatch that = (AdvertWatch) o;

		if (getAdvertWatchType() != that.getAdvertWatchType())
			return false;
		return getWatcherIpAddress() != null
				? getWatcherIpAddress().equals(that.getWatcherIpAddress())
				: that.getWatcherIpAddress() == null;
	}

	@Override
	public String toString() {
		return "AdvertWatch{" + "advertWatchType=" + advertWatchType + ", watcherIpAddress='" + watcherIpAddress + '\''
				+ "} " + super.toString();
	}
}
