/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Represents generic entity functionality for "CRUD".
 *
 * @author Kamleshkumar Patel
 * @version 1, 2016-07-05
 * @version 2, 2018-03-23
 */
public abstract class AbstractGenericDAO {

	// @PersistenceContext(unitName = IPersistence.MYSTORY_JTA_PU)

	@PersistenceContext
	private EntityManager em;

	public AbstractGenericDAO() {
	}

	protected EntityManager getEM() {
		return em;
	}

	/**
	 * Check if the instance is a managed entity instance belonging to the current
	 * persistence context.
	 *
	 * @param entity
	 * @return
	 * @see EntityManager#contains(Object)
	 */
	public <T> boolean isManaged(T entity) {
		return entity != null && em.contains(entity);
	}

	/**
	 * Delete entity with given id.
	 *
	 * @param id
	 * @param entityClass
	 */
	protected final <T> void delete(Object id, Class<T> entityClass) {
		T entityToBeRemoved = em.getReference(entityClass, id);

		em.remove(entityToBeRemoved);
	}

	/**
	 * Save entity.
	 *
	 * @param entity
	 */
	protected final <T> void persist(T entity) {
		em.persist(entity);
	}

	/**
	 * @param entity
	 * @return
	 */
	protected final <T> T merge(T entity) {
		return em.merge(entity);
	}

	/**
	 * @param entityId
	 * @param entityClass
	 * @return
	 */
	public final <T> T find(long entityId, Class<T> entityClass) {
		return em.find(entityClass, entityId);
	}

	/**
	 * @param primaryKey
	 * @param entityClass
	 * @return
	 */
	public final <T> T find(Object primaryKey, Class<T> entityClass) {
		return em.find(entityClass, primaryKey);
	}

	/**
	 * Find entities using named query and query parameters.
	 *
	 * @param namedQuery
	 *            query name
	 * @param queryParams
	 * @param entityClass
	 * @return
	 */
	public final <T> List<T> find(String namedQuery, Map<String, Object> queryParams, Class<T> entityClass) {
		TypedQuery<T> tQ = em.createNamedQuery(namedQuery, entityClass);

		for (Map.Entry<String, Object> param : queryParams.entrySet()) {
			tQ.setParameter(param.getKey(), param.getValue());
		}
		return tQ.getResultList();
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public <T> List<T> findAll(Class<T> entityClass) {
		CriteriaQuery cq = getEM().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEM().createQuery(cq).getResultList();
	}
}
