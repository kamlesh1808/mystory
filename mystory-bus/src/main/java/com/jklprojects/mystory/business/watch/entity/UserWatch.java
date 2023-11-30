/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.api.entity.CreatedUpdatedOffsetDateTime;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.common.user.UserRoleType;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.05.23
 */
@Entity
@Table(name = "S_USER_WATCH")
@NamedQueries({
		@NamedQuery(name = "countUserViews", query = "SELECT COUNT(uw) FROM UserWatch uw "
				+ "WHERE uw.userWatched.uid = :watchedUserUID"),
		@NamedQuery(name = "countUserOAuthViews", query = "SELECT COUNT(uw) FROM UserWatch uw "
				+ "WHERE uw.userOAuth2Watched.idOAuth2 = :watchedUserUID"),
		@NamedQuery(name = "countUserWatchWithUserUID", query = "SELECT COUNT(uw) FROM UserWatch uw "
				+ "INNER JOIN uw.userWatched u " + "WHERE uw.userWatched.uid = :watchedUserUID "
				+ "AND (uw.userWatcher.uid = :watcherUserUID OR uw.userOAuth2Watcher.idOAuth2 = :watcherUserUID) "
				+ "AND uw.watcherIpAddress = :watcherIpAddress AND uw.viewDate = CURRENT_DATE"),
		@NamedQuery(name = "countUserWatchWithUserOAuthUID", query = "SELECT COUNT(uw) FROM UserWatch uw "
				+ "INNER JOIN uw.userOAuth2Watched uaWatched "
				+ "WHERE uw.userOAuth2Watched.idOAuth2 = :watchedUserUID "
				+ "AND (uw.userOAuth2Watcher.idOAuth2 = :watcherUserUID OR uw.userWatcher.uid = :watcherUserUID) "
				+ "AND uw.watcherIpAddress = :watcherIpAddress AND uw.viewDate = CURRENT_DATE"),
		@NamedQuery(name = "countUserWatchWithUserId", query = "SELECT COUNT(uw) FROM UserWatch uw "
				+ "WHERE uw.userWatched.id = :watchedUserId "
				+ "AND (uw.userWatcher.id = :watcherUserId OR uw.userOAuth2Watcher.id = :watcherUserId)"
				+ "AND uw.watcherIpAddress = :watcherIpAddress AND uw.viewDate = CURRENT_DATE"),
		@NamedQuery(name = "countUserWatchWithUserOAuthId", query = "SELECT COUNT(uw) FROM UserWatch uw "
				+ "WHERE uw.userOAuth2Watched.id = :watchedUserOAuthId "
				+ "AND (uw.userOAuth2Watcher.id = :watcherUserOAuthId OR uw.userWatcher.id = :watcherUserOAuthId) "
				+ "AND uw.watcherIpAddress = :watcherIpAddress AND uw.viewDate = CURRENT_DATE")})
public class UserWatch extends AbstractAppEntity {

	private static final long serialVersionUID = 7348889779003257347L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id_watched", insertable = false, updatable = false)
	private User userWatched;

	@Column(name = "user_id_watched")
	private Long userIdWatched;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_oauth2_id_watched", insertable = false, updatable = false)
	private UserOAuth2 userOAuth2Watched;

	@Column(name = "user_oauth2_id_watched")
	private Long userOAuthIdWatched;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id_watcher", insertable = false, updatable = false)
	private User userWatcher;

	@Column(name = "user_id_watcher")
	private Long userIdWatcher;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_oauth2_id_watcher", insertable = false, updatable = false)
	private UserOAuth2 userOAuth2Watcher;

	@Column(name = "user_oauth2_id_watcher")
	private Long userOAuthIdWatcher;

	@Column(name = "view_date")
	private LocalDate viewDate;

	@Column(name = "watcher_ipaddress")
	private String watcherIpAddress;

	/** no-arg default constructor */
	public UserWatch() {
		super();

		setViewDate(LocalDate.now());

		setCreatedUpdated(new CreatedUpdatedOffsetDateTime().setCreatedTimestamp(OffsetDateTime.now()));
	}

	/**
	 * @param watchedUserId
	 * @param watchedUserRoleType
	 * @param watcherUserId
	 * @param watcherUserRoleType
	 * @param watcherIpAddress
	 */
	public UserWatch(long watchedUserId, UserRoleType watchedUserRoleType, long watcherUserId,
			UserRoleType watcherUserRoleType, String watcherIpAddress) {
		this();

		setWatcherIpAddress(watcherIpAddress);

		if (watchedUserRoleType.getUserType().isAppInternal()) {
			setUserIdWatched(watchedUserId);
			setUserOAuthIdWatched(null);
		} else if (watchedUserRoleType.getUserType().isOAuth2External()) {
			setUserIdWatched(null);
			setUserOAuthIdWatched(watchedUserId);
		}

		if (watcherUserRoleType.getUserType().isAppInternal()) {
			setUserIdWatcher(watcherUserId);
			setUserOAuth2Watcher(null);
		} else if (watcherUserRoleType.getUserType().isOAuth2External()) {
			setUserOAuthIdWatcher(watcherUserId);
			setUserIdWatcher(null);
		}
	}

	public UserOAuth2 getUserOAuth2Watched() {
		return userOAuth2Watched;
	}

	public void setUserOAuth2Watched(UserOAuth2 userOAuth2Watched) {
		this.userOAuth2Watched = userOAuth2Watched;
	}

	public UserOAuth2 getUserOAuth2Watcher() {
		return userOAuth2Watcher;
	}

	public void setUserOAuth2Watcher(UserOAuth2 userOauth2Watcher) {
		this.userOAuth2Watcher = userOauth2Watcher;
	}

	public User getUserWatched() {
		return userWatched;
	}

	public void setUserWatched(User userWatched) {
		this.userWatched = userWatched;
	}

	public User getUserWatcher() {
		return userWatcher;
	}

	public void setUserWatcher(User userWatcher) {
		this.userWatcher = userWatcher;
	}

	public LocalDate getViewDate() {
		return viewDate;
	}

	public void setViewDate(LocalDate viewDate) {
		this.viewDate = viewDate;
	}

	public String getWatcherIpAddress() {
		return watcherIpAddress;
	}

	public void setWatcherIpAddress(String watcherIpAddress) {
		this.watcherIpAddress = watcherIpAddress;
	}

	public Long getUserIdWatched() {
		return userIdWatched;
	}

	public void setUserIdWatched(Long userIdWatched) {
		this.userIdWatched = userIdWatched;
	}

	public Long getUserOAuthIdWatched() {
		return userOAuthIdWatched;
	}

	public void setUserOAuthIdWatched(Long userOAuthIdWatched) {
		this.userOAuthIdWatched = userOAuthIdWatched;
	}

	public Long getUserIdWatcher() {
		return userIdWatcher;
	}

	public void setUserIdWatcher(Long userIdWatcher) {
		this.userIdWatcher = userIdWatcher;
	}

	public Long getUserOAuthIdWatcher() {
		return userOAuthIdWatcher;
	}

	public void setUserOAuthIdWatcher(Long userOAuthIdWatcher) {
		this.userOAuthIdWatcher = userOAuthIdWatcher;
	}

	@Override
	public int hashCode() {
		int result = getViewDate().hashCode();
		result = 31 * result + (getWatcherIpAddress() != null ? getWatcherIpAddress().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserWatch userWatch = (UserWatch) o;

		if (!getViewDate().equals(userWatch.getViewDate()))
			return false;
		return getWatcherIpAddress() != null
				? getWatcherIpAddress().equals(userWatch.getWatcherIpAddress())
				: userWatch.getWatcherIpAddress() == null;
	}

	@Override
	public String toString() {
		return "UserWatch{" + "userWatched=" + userWatched + ", userOAuth2Watched=" + userOAuth2Watched
				+ ", userWatcher=" + userWatcher + ", userOAuth2Watcher=" + userOAuth2Watcher + ", viewDate=" + viewDate
				+ ", watcherIpAddress='" + watcherIpAddress + '\'' + "} " + super.toString();
	}
}
