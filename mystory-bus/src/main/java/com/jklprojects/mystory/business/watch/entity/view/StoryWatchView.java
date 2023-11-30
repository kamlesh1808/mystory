/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.business.watch.entity.StoryWatch;
import com.jklprojects.mystory.common.ex.AppCodeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-07-18
 */
public class StoryWatchView extends AbstractAppView<StoryWatch> implements Comparable<StoryWatchView> {

	private static final long serialVersionUID = 361311986629654236L;

	private static final XLogger logger = XLoggerFactory.getXLogger(StoryWatch.class);

	private String clientIpAddress;
	private StoryView story;
	private UserView user;
	private UserOAuth2View userOAuth2;

	private LocalDate viewDate;

	public StoryWatchView() {
		super(StoryWatch.class);
	}

	public static List<StoryWatchView> toViews(Collection<StoryWatch> entities) {
		List<StoryWatchView> views = new ArrayList<>();
		for (StoryWatch e : entities) {
			views.add(new StoryWatchView().setViewFromEntity(e, true));
		}
		return views;
	}

	/**
	 * Compare using created timestamp.
	 *
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(StoryWatchView o) {
		return getCreatedTimestamp().compareTo(o.getCreatedTimestamp());
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
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

	public void setUser(UserView user) {
		this.user = user;
	}

	public UserOAuth2View getUserOAuth2() {
		return userOAuth2;
	}

	public void setUserOAuth2(UserOAuth2View userOAuth2) {
		this.userOAuth2 = userOAuth2;
	}

	public LocalDate getViewDate() {
		return viewDate;
	}

	public void setViewDate(LocalDate viewDate) {
		this.viewDate = viewDate;
	}

	@Override
	public StoryWatch setEntityFromView(StoryWatch entity, boolean setBi) throws AppCodeException {

		entity.setId(getId());
		entity.setClientIpAddress(getClientIpAddress());
		entity.setViewDate(getViewDate());

		if (getUser() != null && getUser().isIdSet()) {
			entity.setUser(getUser().setEntityFromView(new User(), false));
		}

		if (getUserOAuth2() != null && getUserOAuth2().isIdSet()) {
			entity.setUserOAuth2(getUserOAuth2().setEntityFromView(new UserOAuth2(), false));
		}
		return entity;
	}

	/**
	 * Return the updated view based on the given entity.
	 *
	 * @param entity
	 * @return updated view
	 */
	@Override
	public StoryWatchView setViewFromEntity(StoryWatch entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		setClientIpAddress(entity.getClientIpAddress());
		setStory(new StoryView().setViewFromEntity(entity.getStory(), false));
		setViewDate(entity.getViewDate());

		if (setBi) {
			if (entity.getUser() != null && entity.getUser().isIdSet()) {
				setUser(new UserView().setViewFromEntity(entity.getUser(), false));
			}

			if (entity.getUserOAuth2() != null && entity.getUserOAuth2().isIdSet()) {
				setUserOAuth2(new UserOAuth2View().setViewFromEntity(entity.getUserOAuth2(), false));
			}
		}

		return this;
	}

	@Override
	public String toString() {
		return "StoryWatchView{" + "clientIpAddress='" + clientIpAddress + '\'' + ", viewDate=" + viewDate + "} "
				+ super.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof StoryWatchView))
			return false;

		StoryWatchView that = (StoryWatchView) o;

		if (getClientIpAddress() != null
				? !getClientIpAddress().equals(that.getClientIpAddress())
				: that.getClientIpAddress() != null)
			return false;
		return getViewDate() != null ? getViewDate().equals(that.getViewDate()) : that.getViewDate() == null;
	}

	@Override
	public int hashCode() {
		int result = getClientIpAddress() != null ? getClientIpAddress().hashCode() : 0;
		result = 31 * result + (getViewDate() != null ? getViewDate().hashCode() : 0);
		return result;
	}
}
