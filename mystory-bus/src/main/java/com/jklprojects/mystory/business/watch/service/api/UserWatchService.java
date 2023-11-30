/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.service.api;

import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.business.watch.entity.UserWatch;
import com.jklprojects.mystory.common.user.UserRoleType;
import javax.ejb.Local;

/**
 * User Watch services API for CRUD, etc.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2018-05-23
 * @since 2.18.05.23
 */
@Local
public interface UserWatchService {

	/**
	 * Create User or {@link User} or {@link UserOAuth2} {@link UserWatch}, if it
	 * does not exist.
	 *
	 * @param watchedUserId
	 * @param watchedUserRoleType
	 * @param watcherUserId
	 * @param watcherUserRoleType
	 * @param watcherIpAddress
	 */
	void createUserWatch(long watchedUserId, UserRoleType watchedUserRoleType, long watcherUserId,
			UserRoleType watcherUserRoleType, String watcherIpAddress);

	/**
	 * Determine if a user watch exists.
	 *
	 * @param watchedUserUID
	 * @param watchedUserRoleType
	 * @param watcherUserUID
	 * @param watcherIpAddress
	 * @return true if user watch exists, false otherwise.
	 */
	boolean isUserWatchExistsWithUserUID(String watchedUserUID, UserRoleType watchedUserRoleType, String watcherUserUID,
			String watcherIpAddress);

	/**
	 * Get {@link User} or {@link UserOAuth2} {@link UserWatch} count.
	 *
	 * @param watchedUserUID
	 * @return
	 */
	long countUserViews(long watchedUserUID);

	long countUserOAuthViews(String watchedUserUID);
}
