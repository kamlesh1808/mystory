/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.ex;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Jul 28, 2015
 */
public class MyStoryExFactory extends ExceptionHandlerFactory {
	private final ExceptionHandlerFactory parent;

	public MyStoryExFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new MyStoryExHandler(parent.getExceptionHandler());
	}
}
