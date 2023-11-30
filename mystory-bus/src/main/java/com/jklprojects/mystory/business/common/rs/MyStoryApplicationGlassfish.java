/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.rs;

import com.jklprojects.mystory.business.story.service.rs.StoriesRS;
import com.jklprojects.mystory.business.story.service.rs.TagsRS;
import com.jklprojects.mystory.business.user.service.rs.UsersRS;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-04-23
 * @version 1, 2018-04-23
 */
@ApplicationPath("/rest-gf")
public class MyStoryApplicationGlassfish extends Application {

	private final Set<Class<?>> classess = new HashSet<Class<?>>();

	public MyStoryApplicationGlassfish() {
		classess.add(UsersRS.class);
		classess.add(StoriesRS.class);
		classess.add(TagsRS.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classess;
	}
}
