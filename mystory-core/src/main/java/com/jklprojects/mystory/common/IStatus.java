/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-05-03
 * @version 4, 2019-03-23
 */
public interface IStatus {

	int getId();

	String getStatusName();

	/**
	 * Return true if status id is equal, false otherwise.
	 *
	 * @param id
	 * @return true if status id is equal, false otherwise.
	 */
	boolean isIdEqual(int id);

	boolean in(IStatus... statuses);
}
