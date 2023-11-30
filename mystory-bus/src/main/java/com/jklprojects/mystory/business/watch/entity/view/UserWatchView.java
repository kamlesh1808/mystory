/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.business.watch.entity.UserWatch;
import com.jklprojects.mystory.common.ex.AppCodeException;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.05.23
 */
public class UserWatchView extends AbstractAppView<UserWatch> {

	private static final long serialVersionUID = -6206446632702081923L;

	private UserView userWatched;
	private UserOAuth2View userOAuth2Watched;

	private UserView userWatcher;
	private UserOAuth2View userOAuth2Watcher;

	private String watcherIpAddress;

	public UserWatchView(Class<UserWatch> entityClass) {
		super(entityClass);
	}

	public UserOAuth2View getUserOAuth2Watched() {
		return userOAuth2Watched;
	}

	public UserOAuth2View getUserOAuth2Watcher() {
		return userOAuth2Watcher;
	}

	public UserView getUserWatched() {
		return userWatched;
	}

	public UserView getUserWatcher() {
		return userWatcher;
	}

	public String getWatcherIpAddress() {
		return watcherIpAddress;
	}

	@Override
	public UserWatch setEntityFromView(UserWatch entity, boolean setBi) throws AppCodeException {
		entity.setWatcherIpAddress(getWatcherIpAddress());

		if (getUserWatched() != null && getUserWatched().isIdSet()) {
			entity.setUserWatched(getUserWatched().setEntityFromView(new User(), false));
		}

		if (getUserOAuth2Watched() != null && getUserOAuth2Watched().isIdSet()) {
			entity.setUserOAuth2Watched(getUserOAuth2Watched().setEntityFromView(new UserOAuth2(), false));
		}

		if (getUserWatcher() != null && getUserWatcher().isIdSet()) {
			entity.setUserWatcher(getUserWatcher().setEntityFromView(new User(), false));
		}

		if (getUserOAuth2Watcher() != null && getUserOAuth2Watcher().isIdSet()) {
			entity.setUserOAuth2Watcher(getUserOAuth2Watcher().setEntityFromView(new UserOAuth2(), false));
		}

		return entity;
	}

	public void setUserOAuth2Watched(UserOAuth2View userOAuth2Watched) {
		this.userOAuth2Watched = userOAuth2Watched;
	}

	public void setUserOAuth2Watcher(UserOAuth2View userOAuth2Watcher) {
		this.userOAuth2Watcher = userOAuth2Watcher;
	}

	public void setUserWatched(UserView userWatched) {
		this.userWatched = userWatched;
	}

	public void setUserWatcher(UserView userWatcher) {
		this.userWatcher = userWatcher;
	}

	@Override
	public UserWatchView setViewFromEntity(UserWatch entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		setWatcherIpAddress(entity.getWatcherIpAddress());

		if (setBi) {
			if (entity.getUserWatched() != null && entity.getUserWatched().isIdSet()) {
				setUserWatched(new UserView().setViewFromEntity(entity.getUserWatched(), false));
			}

			if (entity.getUserOAuth2Watched() != null && entity.getUserOAuth2Watched().isIdSet()) {
				setUserOAuth2Watched(new UserOAuth2View().setViewFromEntity(entity.getUserOAuth2Watched(), false));
			}

			if (entity.getUserWatcher() != null && entity.getUserWatcher().isIdSet()) {
				setUserWatcher(new UserView().setViewFromEntity(entity.getUserWatcher(), false));
			}

			if (entity.getUserOAuth2Watcher() != null && entity.getUserOAuth2Watcher().isIdSet()) {
				setUserOAuth2Watcher(new UserOAuth2View().setViewFromEntity(entity.getUserOAuth2Watcher(), false));
			}
		}

		return this;
	}

	public void setWatcherIpAddress(String watcherIpAddress) {
		this.watcherIpAddress = watcherIpAddress;
	}
}
