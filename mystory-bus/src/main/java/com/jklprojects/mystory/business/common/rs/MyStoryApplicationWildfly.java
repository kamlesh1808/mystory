/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.rs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-04-23
 * @version 1, 2018-10-23
 */
@ApplicationPath(AbstractRTO.APPLICATION_PATH)
public class MyStoryApplicationWildfly extends Application {
	/*
	 * For Wildfly 10.0.1 + (Java EE 7) - There is no need to @Override public
	 * Set<Class<?>> getClasses() and return a Set of RESTful classes
	 */
}
