/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.watch.service;

import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.business.watch.entity.PageWatch;
import com.jklprojects.mystory.business.watch.service.api.PageWatchService;
import com.jklprojects.mystory.common.AppPage;
import com.jklprojects.mystory.common.user.UserRoleType;
import javax.ejb.Stateless;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.slf4j.profiler.Profiler;

/**
 * Page Watch services for CRUD, etc.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.06.23
 */
@Stateless
public class PageWatchServiceImpl extends AppEntityDAO<PageWatch> implements PageWatchService {

	private static final XLogger logger = XLoggerFactory.getXLogger(PageWatchServiceImpl.class);

	public PageWatchServiceImpl() {
		super(PageWatch.class);
	}

	@Override
	public void createPageWatch(AppPage appPage, long watcherUserId, UserRoleType watcherUserRoleType,
			String watcherIpAddress) {

		logger.entry(appPage, watcherUserId, watcherUserRoleType, watcherIpAddress);

		if (!isPageWatchExists(appPage, watcherUserId, watcherUserRoleType, watcherIpAddress)) {
			PageWatch pageWatchE = new PageWatch(appPage, watcherUserId, watcherUserRoleType, watcherIpAddress);

			PageWatch pageWatchCreated = createEntity(pageWatchE);
			if (pageWatchCreated != null && pageWatchCreated.isIdSet()) {
				logger.info("Page Watch created: {} ", pageWatchE.toString());
			}
		}
	}

	@Override
	public boolean isPageWatchExists(AppPage appPage, long watcherUserId, UserRoleType watcherUserRoleType,
			String watcherIpAddress) {

		Profiler profiler = new Profiler("isPageWatchExists");
		profiler.setLogger(logger);

		logger.entry(appPage, watcherUserId, watcherUserRoleType, watcherIpAddress);

		boolean exists = false;
		long countPageWatch = getEM().createNamedQuery("countPageWatchUser", Long.class)
				.setParameter("appPage", appPage).setParameter("watcherUserId", watcherUserId)
				.setParameter("watcherIpAddress", watcherIpAddress.trim()).getSingleResult();

		if (countPageWatch > 0) {
			logger.info(
					"A Page Watch exists. page: {} watcherUserId: {}, watcherUserRoleType: {}, watcherIpAddress: {}",
					appPage, watcherUserId, watcherUserRoleType, watcherIpAddress);
			exists = true;
		}

		profiler.stop().log();
		return exists;
	}
}
