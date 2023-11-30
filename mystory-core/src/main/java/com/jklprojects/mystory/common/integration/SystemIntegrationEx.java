/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.integration;

import com.jklprojects.mystory.common.ex.AppException;

/**
 * @author Kamleshkumar Patel
 * @version 1, May 2, 2016
 */
public class SystemIntegrationEx extends AppException {

	private static final long serialVersionUID = -468345977176292859L;

	public SystemIntegrationEx(String message) {
		super(message);
	}

	public SystemIntegrationEx(String message, Throwable t) {
		super(message, t);
	}
}
