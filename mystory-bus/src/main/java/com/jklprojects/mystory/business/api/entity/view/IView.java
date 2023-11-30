/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity.view;

import com.jklprojects.mystory.common.ex.AppCodeException;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 1, 2015
 */
public interface IView<E, V> {

	/**
	 * Return the updated view based on the given entity.
	 *
	 * @param entity
	 * @param setBi
	 * @return updated view
	 */
	V setViewFromEntity(E entity, boolean setBi);

	/**
	 * Return the updated entity based on the view.
	 *
	 * @param entity
	 * @param setBi
	 * @return the updated entity based on the view.
	 * @throws AppCodeException
	 */
	E setEntityFromView(E entity, boolean setBi) throws AppCodeException;
}
