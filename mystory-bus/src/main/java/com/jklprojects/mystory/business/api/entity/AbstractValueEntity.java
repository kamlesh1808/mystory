/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

/**
 * A @MappedSuperclass entity whose properties will be inherited by the sub
 * classes. Entities needing {@code created_timestamp} and
 * {@code updated_timestamp} properties should inherit from this entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-06-23
 */
@MappedSuperclass
public abstract class AbstractValueEntity extends AbstractIdEntity {

	private static final long serialVersionUID = 6257762340616142259L;

	@Embedded
	private CreatedUpdatedOffsetDateTime createdUpdated;

	public CreatedUpdatedOffsetDateTime getCreatedUpdated() {
		return createdUpdated;
	}

	public void setCreatedUpdated(CreatedUpdatedOffsetDateTime createdUpdated) {
		this.createdUpdated = createdUpdated;
	}
}
