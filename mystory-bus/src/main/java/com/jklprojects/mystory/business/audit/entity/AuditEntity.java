/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.story.entity.Story;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-11-06
 * @version 2, 2018-05-23
 */
public enum AuditEntity {
	NONE(0, null), USER(1, User.class), STORY(2, Story.class), USER_OAUTH2(3, UserOAuth2.class);

	private final int id;
	private final Class<? extends AbstractAppEntity> entityClass;

	AuditEntity(int id, Class<? extends AbstractAppEntity> entityClass) {
		this.id = id;
		this.entityClass = entityClass;
	}

	public int getId() {
		return id;
	}

	/**
	 * Lookup and return matching object or null if match is not found.
	 *
	 * @param id
	 * @return matching status or null if match is not found.
	 */
	public static AuditEntity toEnum(int id) {
		for (AuditEntity en : AuditEntity.values()) {
			if (en.getId() == id) {
				return en;
			}
		}

		return null;
	}

	public Class<? extends AbstractAppEntity> getEntityClass() {
		return entityClass;
	}
}
