/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents generic "CRUD" functionality for entity.
 *
 * @author Kamleshkumar Patel
 * @version 1, 2015-04-04
 * @version 1, 2017-01-23
 * @version 1, 2017-11-23
 * @version 2, 2018-04-23
 */
public abstract class AbstractEntityDAO<E> extends AbstractGenericDAO {
	private static final XLogger logger = XLoggerFactory.getXLogger(AbstractEntityDAO.class);

	private final Class<E> entityClass;
	private final String entityClassSimpleName;

	/**
	 * @param entityClass
	 */
	public AbstractEntityDAO(Class<E> entityClass) {
		this.entityClass = entityClass;
		this.entityClassSimpleName = entityClass.getSimpleName();
	}

	public E find(long entityID) {
		return getEM().find(entityClass, entityID);
	}

	public <T extends AbstractIdEntity> T findEntity(long entityID, Class<T> entityClazz) {
		return getEM().find(entityClazz, entityID);
	}

	// Using the unchecked because JPA does not have a
	// em.getCriteriaBuilder().createQuery()<T> method
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<E> findEntityAll() {
		CriteriaQuery cq = getEM().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEM().createQuery(cq).getResultList();
	}

	/**
	 * @param orderByColumn
	 * @param order
	 * @return
	 */
	public List<E> findEntityAll(String orderByColumn, String order) {
		String query = "select o from " + entityClassSimpleName + " o "
				+ (orderByColumn != null && !orderByColumn.isEmpty() ? " order by o." + orderByColumn : "")
				+ (order != null && !order.isEmpty() ? " " + order : "");
		return getEM().createQuery(query, entityClass).getResultList();
	}

	/**
	 * Update entity
	 *
	 * @param entity
	 * @return
	 */
	public E mergeEntity(E entity) {
		return getEM().merge(entity);
	}

	public <T extends AbstractIdEntity> T updateEntity(T entity) {
		return merge(entity);
	}

	/**
	 * Persist entity.
	 *
	 * @param entity
	 * @return persisted entity
	 */
	protected E create(E entity) {
		logger.entry(entity);

		persist(entity);

		return entity;
	}

	/**
	 * Persist entity.
	 *
	 * @param entity
	 * @return persisted entity
	 */
	protected <T extends AbstractAppEntity> T createEntity(T entity) {
		logger.entry(entity);

		persist(entity);

		return entity;
	}

	/**
	 * Delete entity with given id.
	 *
	 * @param id
	 * @param classe
	 */
	protected void delete(Object id) {
		E entityToBeRemoved = getEM().getReference(entityClass, id);

		getEM().remove(entityToBeRemoved);
	}

	protected String getEntityClassSimpleName() {
		return entityClassSimpleName;
	}

	/**
	 * Update entity.
	 *
	 * @param entity
	 * @return
	 */
	protected E update(E entity) {
		return merge(entity);
	}
}
