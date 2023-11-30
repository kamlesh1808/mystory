/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity.view;

import java.io.Serializable;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 19, 2015
 */
public abstract class AbstractView<E> implements Serializable, IView<E, AbstractView<E>> {

	private static final long serialVersionUID = 7828137418694953716L;

	private final Class<E> entityClass;

	public AbstractView(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}
}
