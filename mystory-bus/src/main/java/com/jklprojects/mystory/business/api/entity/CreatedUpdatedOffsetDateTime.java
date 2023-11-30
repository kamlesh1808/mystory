/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @see OffsetDateTime
 * @author Kamleshkumar N. Patel
 * @version 1, July 6, 2015
 * @version 1, 2016-DEC-31
 * @version 1, 2017-Nov-23
 */
@Embeddable
public class CreatedUpdatedOffsetDateTime implements Serializable {
	private static final long serialVersionUID = -4636104922616832447L;

	@Column(name = "created_timestamp")
	private OffsetDateTime createdTimestamp;

	@Column(name = "updated_timestamp")
	private OffsetDateTime updatedTimestamp;

	public CreatedUpdatedOffsetDateTime() {
		super();
	}

	public OffsetDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public CreatedUpdatedOffsetDateTime setCreatedTimestamp(OffsetDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;

		return this;
	}

	public OffsetDateTime getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(OffsetDateTime updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public int getCreatedYear() {
		return createdTimestamp.getYear();
	}

	public int getCreatedMonth() {
		return createdTimestamp.getMonth().getValue();
	}

	@Override
	public String toString() {
		return "CreatedUpdatedOffsetDateTime ["
				+ (createdTimestamp != null ? "createdTimestamp=" + createdTimestamp + ", " : "")
				+ (updatedTimestamp != null ? "updatedTimestamp=" + updatedTimestamp : "") + "]";
	}
}
