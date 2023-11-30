/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.service;

import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.business.user.service.api.UserOAuth2Service;
import com.jklprojects.mystory.business.user.service.api.UserService;
import com.jklprojects.mystory.business.watch.entity.UserWatch;
import com.jklprojects.mystory.business.watch.service.api.UserWatchService;
import com.jklprojects.mystory.common.user.UserRoleType;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * User Watch services for CRUD, etc.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-04-23
 * @since 2, 2.18.05.23
 */
@Stateless
public class UserWatchServiceImpl extends AppEntityDAO<UserWatch> implements UserWatchService {

	private static final XLogger logger = XLoggerFactory.getXLogger(UserWatchServiceImpl.class);

	@Inject
	private UserService userService;

	@Inject
	private UserOAuth2Service userOAuth2Service;

	public UserWatchServiceImpl() {
		super(UserWatch.class);
	}

	@Override
	public void createUserWatch(long watchedUserId, UserRoleType watchedUserRoleType, long watcherUserId,
			UserRoleType watcherUserRoleType, String watcherIpAddress) {

		logger.entry(watchedUserId, watchedUserRoleType, watcherUserId, watcherUserRoleType, watcherIpAddress);

		if (!isUserWatchExistsWithUserId(watchedUserId, watchedUserRoleType, watcherUserId, watcherIpAddress)) {
			UserWatch userWatchE = new UserWatch(watchedUserId, watchedUserRoleType, watcherUserId, watcherUserRoleType,
					watcherIpAddress);

			UserWatch userWatchCreated = createEntity(userWatchE);
			if (userWatchCreated != null && userWatchCreated.isIdSet()) {
				logger.info("User Watch created: {} ", userWatchE.toString());
			}
		}
	}

	@Deprecated
	@Override
	public boolean isUserWatchExistsWithUserUID(String watchedUserUID, UserRoleType watchedUserRoleType,
			String watcherUserUID, String watcherIpAddress) {

		logger.entry(watcherUserUID, watchedUserRoleType, watcherIpAddress, watchedUserUID);

		boolean exists = false;

		long userWatchCount = 0;

		if (watchedUserRoleType.getUserType().isAppInternal()) {
			userWatchCount = getEM().createNamedQuery("countUserWatchWithUserUID", Long.class)
					.setParameter("watchedUserUID", watchedUserUID).setParameter("watcherUserUID", watcherUserUID)
					.setParameter("watcherIpAddress", watcherIpAddress.trim()).getSingleResult();

		} else if (watchedUserRoleType.getUserType().isOAuth2External()) {
			userWatchCount = getEM().createNamedQuery("countUserWatchWithUserOAuthUID", Long.class)
					.setParameter("watchedUserUID", watchedUserUID).setParameter("watcherUserUID", watcherUserUID)
					.setParameter("watcherIpAddress", watcherIpAddress.trim()).getSingleResult();
		}

		if (userWatchCount > 0) {
			logger.info(
					"A User Watch exists watcherUserUID: {}, watcherIpAddress: {}, watcherUserRoleType: {}, watchedUserUID: {}",
					watcherUserUID, watcherIpAddress, watchedUserRoleType, watchedUserUID);
			exists = true;
		}

		return exists;
	}

	@Override
	public long countUserViews(long watchedUserUID) {
		logger.entry(watchedUserUID);

		return getEM().createNamedQuery("countUserViews", Long.class).setParameter("watchedUserUID", watchedUserUID)
				.getSingleResult();
	}

	@Override
	public long countUserOAuthViews(String watchedUserUID) {
		logger.entry(watchedUserUID);

		return getEM().createNamedQuery("countUserOAuthViews", Long.class)
				.setParameter("watchedUserUID", watchedUserUID).getSingleResult();
	}

	public boolean isUserWatchExistsWithUserId(long watchedUserId, UserRoleType watchedUserRoleType, long watcherUserId,
			String watcherIpAddress) {

		logger.entry(watchedUserId, watchedUserRoleType, watcherUserId, watcherIpAddress);

		boolean exists = false;
		long userWatchCount = 0;

		if (watchedUserRoleType.getUserType().isAppInternal()) {
			userWatchCount = getEM().createNamedQuery("countUserWatchWithUserId", Long.class)
					.setParameter("watcherUserId", watcherUserId).setParameter("watchedUserId", watchedUserId)
					.setParameter("watcherIpAddress", watcherIpAddress.trim()).getSingleResult();

		} else if (watchedUserRoleType.getUserType().isOAuth2External()) {
			userWatchCount = getEM().createNamedQuery("countUserWatchWithUserOAuthId", Long.class)
					.setParameter("watcherUserOAuthId", watcherUserId).setParameter("watchedUserOAuthId", watchedUserId)
					.setParameter("watcherIpAddress", watcherIpAddress.trim()).getSingleResult();
		}

		if (userWatchCount > 0) {
			logger.info(
					"A User Watch exists watcherUserId: {}, watchedUserRoleType: {}, watcherIpAddress: {}, watchedUserId: {}",
					watcherUserId, watchedUserRoleType, watcherIpAddress, watcherUserId);
			exists = true;
		}

		return exists;
	}
}
