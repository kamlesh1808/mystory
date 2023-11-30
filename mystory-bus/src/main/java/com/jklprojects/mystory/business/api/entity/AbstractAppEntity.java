/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import com.jklprojects.mystory.business.audit.Auditable;
import com.jklprojects.mystory.business.audit.entity.AuditEntity;

/**
 * A @MappedSuperclass entity whose properties will be inherited by the sub
 * classes. Entities needing {@code created_timestamp} and
 * {@code updated_timestamp} properties should inherit from this entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-05-01
 */
@MappedSuperclass
public abstract class AbstractAppEntity extends AbstractIdEntity {
	private static final long serialVersionUID = -1112131654942148754L;

	@Transient
	private boolean auditable;

	@Transient
	private AuditEntity auditEntityName;

	@Embedded
	private CreatedUpdatedOffsetDateTime createdUpdated = new CreatedUpdatedOffsetDateTime();

	@Transient
	private List<Field> auditableFields = new ArrayList<>();

	{ // non-static block
		Auditable auditableA = getClass().getDeclaredAnnotation(Auditable.class);
		if (auditableA != null) {
			setAuditable(true);
			setAuditEntityName(auditableA.name());

		}
	}

	public AbstractAppEntity() {
		if (auditableFields.isEmpty()) {
			auditableFields = PersistenceUtil.getAuditableFields(this.getClass());
		}
	}

	public List<Field> getAuditableFields() {
		return auditableFields;
	}

	public CreatedUpdatedOffsetDateTime getCreatedUpdated() {
		if (createdUpdated == null) {
			createdUpdated = new CreatedUpdatedOffsetDateTime();
		}
		return createdUpdated;
	}

	public void setCreatedUpdated(CreatedUpdatedOffsetDateTime createdUpdated) {
		this.createdUpdated = createdUpdated;
	}

	public AuditEntity getAuditEntityName() {
		return auditEntityName;
	}

	public void setAuditEntityName(AuditEntity auditEntityName) {
		this.auditEntityName = auditEntityName;
	}

	@PrePersist
	public void prePersist() {
		if (getCreatedUpdated().getCreatedTimestamp() == null) {
			getCreatedUpdated().setCreatedTimestamp(OffsetDateTime.now());
		}
	}

	@Override
	public String toString() {
		return "AbstractAppEntity{} " + super.toString();
	}

	@PreUpdate
	public void preUpdate() {
		getCreatedUpdated().setUpdatedTimestamp(OffsetDateTime.now());
	}

	public boolean isAuditable() {
		return auditable;
	}

	/**
	 * private for good reason.
	 *
	 * @param auditable
	 */
	private void setAuditable(boolean auditable) {
		this.auditable = auditable;
	}

	public String getCreatedTimestampFormatted() {
		return createdUpdated != null && createdUpdated.getCreatedTimestamp() != null
				? DateTimeFormatter.RFC_1123_DATE_TIME.format(createdUpdated.getCreatedTimestamp())
				: "";
	}

	public String getUpdatedTimestampFormatted() {
		return createdUpdated != null && createdUpdated.getUpdatedTimestamp() != null
				? DateTimeFormatter.RFC_1123_DATE_TIME.format(createdUpdated.getUpdatedTimestamp())
				: "";
	}

}
