/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity.view;

import com.jklprojects.mystory.business.api.entity.AbstractIdEntity;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 1, 2015
 */
public abstract class AbstractIDView<E extends AbstractIdEntity> extends AbstractView<E> {

	private static final long serialVersionUID = 7975058175161748257L;

	private long id;

	public AbstractIDView(Class<E> entityClass) {
		super(entityClass);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractIDView [getId()=").append(getId()).append("]");
		return builder.toString();
	}

	/**
	 * Return the updated view based on the given entity.
	 *
	 * @param entity
	 * @param setBi
	 * @return updated view
	 */
	@Override
	public AbstractIDView<E> setViewFromEntity(E entity, boolean setBi) {
		this.setId(entity.getId());

		return this;
	}

	public final long getId() {
		return id;
	}

	public final void setId(long id) {
		this.id = id;
	}

	public boolean isIdSet() {
		return getId() > 0;
	}
}
