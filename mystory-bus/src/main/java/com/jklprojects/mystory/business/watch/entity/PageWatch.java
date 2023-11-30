/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.api.entity.CreatedUpdatedOffsetDateTime;
import com.jklprojects.mystory.business.common.entity.converter.AppPageAttrConverter;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.common.AppPage;
import com.jklprojects.mystory.common.user.UserRoleType;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * PageWatch entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.06.23
 */
@Entity
@Table(name = "A_PAGE_WATCH")
@NamedQueries({@NamedQuery(name = "countPageWatchUser", query = "SELECT COUNT(pw) FROM PageWatch pw "
		+ "WHERE pw.appPage = :appPage "
		+ "AND (pw.userWatcher.id = :watcherUserId OR pw.userOAuth2Watcher.id = :watcherUserId)"
		+ "AND pw.watcherIpAddress = :watcherIpAddress AND pw.viewDate = CURRENT_DATE"),})
public class PageWatch extends AbstractAppEntity {

	private static final long serialVersionUID = -5930226938130284895L;

	@Column(name = "page_id")
	@Convert(converter = AppPageAttrConverter.class)
	private AppPage appPage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id_watcher", insertable = false, updatable = false)
	private User userWatcher;

	@Column(name = "user_id_watcher")
	private Long watcherUserId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_oauth2_id_watcher", insertable = false, updatable = false)
	private UserOAuth2 userOAuth2Watcher;

	@Column(name = "user_oauth2_id_watcher")
	private Long watcherUserOAuthId;

	@Column(name = "view_date")
	private LocalDate viewDate;

	@Column(name = "watcher_ipaddress")
	private String watcherIpAddress;

	/** no-arg default constructor */
	public PageWatch() {
		super();
	}

	/**
	 * @param appPage
	 * @param watcherUserId
	 * @param watcherUserRoleType
	 * @param watcherIpAddress
	 */
	public PageWatch(AppPage appPage, Long watcherUserId, UserRoleType watcherUserRoleType, String watcherIpAddress) {
		this();

		setViewDate(LocalDate.now());
		setCreatedUpdated(new CreatedUpdatedOffsetDateTime().setCreatedTimestamp(OffsetDateTime.now()));

		setAppPage(appPage);
		setWatcherIpAddress(watcherIpAddress);

		if (watcherUserRoleType.getUserType().isAppInternal()) {
			this.watcherUserId = watcherUserId;
		} else if (watcherUserRoleType.getUserType().isOAuth2External()) {
			this.watcherUserOAuthId = watcherUserId;
		}
	}

	public AppPage getAppPage() {
		return appPage;
	}

	public void setAppPage(AppPage appPage) {
		this.appPage = appPage;
	}

	public UserOAuth2 getUserOAuth2Watcher() {
		return userOAuth2Watcher;
	}

	public void setUserOAuth2Watcher(UserOAuth2 userOAuth2Watcher) {
		this.userOAuth2Watcher = userOAuth2Watcher;
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

	@Override
	public int hashCode() {
		int result = getAppPage() != null ? getAppPage().hashCode() : 0;
		result = 31 * result + (getViewDate() != null ? getViewDate().hashCode() : 0);
		result = 31 * result + (getWatcherIpAddress() != null ? getWatcherIpAddress().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof PageWatch))
			return false;

		PageWatch pageWatch = (PageWatch) o;

		if (getAppPage() != pageWatch.getAppPage())
			return false;
		if (getViewDate() != null ? !getViewDate().equals(pageWatch.getViewDate()) : pageWatch.getViewDate() != null)
			return false;
		return getWatcherIpAddress() != null
				? getWatcherIpAddress().equals(pageWatch.getWatcherIpAddress())
				: pageWatch.getWatcherIpAddress() == null;
	}

	@Override
	public String toString() {
		return "PageWatch{" + "appPage=" + appPage + ", userWatcher=" + userWatcher + ", userOAuth2Watcher="
				+ userOAuth2Watcher + ", viewDate=" + viewDate + ", watcherIpAddress='" + watcherIpAddress + '\'' + "} "
				+ super.toString();
	}
}
