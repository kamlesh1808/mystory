/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.api.entity.PersistenceUtil;
import com.jklprojects.mystory.business.story.entity.Story;
import com.jklprojects.mystory.business.story.service.StoryServiceImpl;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.service.UserServiceImpl;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-11-10
 * @version 3, 2018-10-23
 */
@DisplayName("Persistence Util Test")
class PersistenceUtilTest {

	@Test
	@DisplayName("Test Is Persitent Class")
	void testIsPersitentClass() {
		assertTrue(PersistenceUtil.isPersistenceClass(User.class));
		assertTrue(PersistenceUtil.isPersistenceClass(Story.class));
		assertFalse(PersistenceUtil.isPersistenceClass(AbstractAppEntity.class));
		assertFalse(PersistenceUtil.isPersistenceClass(UserServiceImpl.class));
		assertFalse(PersistenceUtil.isPersistenceClass(StoryServiceImpl.class));
	}

	@Test
	@DisplayName("Test Get Auditable Fields")
	void testGetAuditableFields() {
		List<Field> persistenceFields = PersistenceUtil.getAuditableFields(User.class);
		assertTrue(!persistenceFields.isEmpty());
		/*
		 * for (Field pField : persistenceFields) {
		 * System.out.println(pField.getName()); }
		 */
	}
}
