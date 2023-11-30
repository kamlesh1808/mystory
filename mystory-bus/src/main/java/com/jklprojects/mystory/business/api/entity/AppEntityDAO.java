/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.audit.api.AuditService;
import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.common.ex.AppCodeException;
import javax.ejb.EJB;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents generic "CRUD" functionality for App entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-05-01
 * @version 2, 2018-05-23
 */
public abstract class AppEntityDAO<E extends AbstractAppEntity> extends AbstractEntityDAO<E> {

	private static final XLogger logger = XLoggerFactory.getXLogger(AppEntityDAO.class);

	@EJB
	private AppConfigService appConfigService;

	@EJB
	private AuditService auditService;

	public AppEntityDAO(Class<E> entityClass) {
		super(entityClass);
	}

	protected AppConfigService getAppConfigService() {
		return appConfigService;
	}

	protected AuditService getAuditService() {
		return auditService;
	}

	/**
	 * Update entity.
	 *
	 * @param entity
	 * @return
	 */
	protected <T extends AbstractAppEntity> T updateEntity(T entity) {
		logger.entry(entity);
		return merge(entity);
	}

	/**
	 * Find an entity using the view id, update the entity from view and persist
	 * entity.
	 *
	 * @param view
	 * @return
	 * @throws AppCodeException
	 */
	protected <V extends AbstractAppView<E>> E updateWithView(V view) throws AppCodeException {

		// find the entity
		E findEntity = find(view.getId());

		// update the entity from view
		view.setEntityFromView(findEntity, true);

		// update db and commit
		update(findEntity);

		return findEntity;
	}
}
