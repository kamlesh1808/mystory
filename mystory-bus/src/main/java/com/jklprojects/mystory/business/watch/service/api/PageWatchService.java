/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.service.api;

import com.jklprojects.mystory.common.AppPage;
import com.jklprojects.mystory.common.user.UserRoleType;
import javax.ejb.Local;

/**
 * Page Watch services API for CRUD, etc.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.06.23
 */
@Local
public interface PageWatchService {

	void createPageWatch(AppPage appPage, long watcherUserId, UserRoleType watcherUserRoleType,
			String watcherIpAddress);

	boolean isPageWatchExists(AppPage appPage, long watcherUserId, UserRoleType watcherUserRoleType,
			String watcherIpAddress);
}
