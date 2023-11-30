/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

/**
 * A @MappedSuperclass entity whose properties will be inherited by the sub
 * classes. Entities needing {@code created_timestamp} and
 * {@code updated_timestamp} properties should inherit from this entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, July 6, 2015
 * @version 2, 2017-Nov-23
 */
@MappedSuperclass
public abstract class AbstractTableEntity implements Serializable {
	private static final long serialVersionUID = 9051516540571918733L;

	@Embedded
	private CreatedUpdatedOffsetDateTime createdUpdated;

	public CreatedUpdatedOffsetDateTime getCreatedUpdated() {
		return createdUpdated;
	}

	public void setCreatedUpdated(CreatedUpdatedOffsetDateTime createdUpdated) {
		this.createdUpdated = createdUpdated;
	}
}
