/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.common.audit.AuditType;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-11-06
 */
@Entity
@Table(name = "A_AUDIT")
@NamedQueries({@NamedQuery(name = "findUserLastSeen", query = "SELECT MAX(audit.createdUpdated.createdTimestamp) "
		+ "FROM Audit audit WHERE audit.userUID = :userUID AND audit.auditType IN :auditTypes")})
public class Audit extends AbstractAppEntity {

	public static final int AUDIT_VALUE_LENGTH = 1024;
	private static final long serialVersionUID = -4205565362617084554L;
	private static final XLogger logger = XLoggerFactory.getXLogger(Audit.class);

	@Column(name = "entity_id", nullable = false)
	private long entityId;

	@Column(name = "user_uid")
	private String userUID;

	@Column(name = "audit_type", nullable = false)
	@Convert(converter = AuditTypeAttrConverter.class)
	private AuditType auditType;

	@Column(name = "event_type", nullable = false)
	@Convert(converter = AuditEventTypeAttrConverter.class)
	private AuditEventType eventType;

	@OneToMany(mappedBy = "audit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<AuditDetail> auditDetails = new HashSet<AuditDetail>();

	@Column(name = "a_entity_id", nullable = false)
	@Convert(converter = AuditEntityAttrConverter.class)
	private AuditEntity auditEntity;

	public Audit() {
		super();
	}

	public <E extends AbstractAppEntity> Audit(E entity, AuditType auditType, AuditEventType auditEventType) {
		this();

		setEntityId(entity != null ? entity.getId() : 0);
		setEventType(auditEventType);
		getCreatedUpdated().setCreatedTimestamp(OffsetDateTime.now());
		setAuditEntity(entity != null ? entity.getAuditEntityName() : AuditEntity.NONE);
		setAuditType(auditType);
	}

	public <E extends AbstractAppEntity> Audit(E entity, AuditType auditType, AuditEventType auditEventType,
			String userUID) {
		this();

		setEntityId(entity != null ? entity.getId() : 0);
		setEventType(auditEventType);
		getCreatedUpdated().setCreatedTimestamp(OffsetDateTime.now());
		setAuditEntity(entity != null ? entity.getAuditEntityName() : AuditEntity.NONE);
		setAuditType(auditType);
		setUserUID(userUID);
	}

	public <E extends AbstractAppEntity> Audit(long entityId, AuditType auditType, AuditEventType auditEventType,
			AuditEntity auditEntity, String userUID) {
		this();

		setEntityId(entityId);
		setEventType(auditEventType);
		getCreatedUpdated().setCreatedTimestamp(OffsetDateTime.now());
		setAuditEntity(auditEntity);
		setAuditType(auditType);
		setUserUID(userUID);
	}

	public void addAuditDetail(AuditDetail ad) {
		if (!auditDetails.contains(ad)) {
			logger.trace("addAuditDetail {}", ad.toString());
			auditDetails.add(ad);
			ad.setAudit(this);
		}
	}

	public Set<AuditDetail> getAuditDetails() {
		return this.auditDetails;
	}

	public void setAuditDetails(Set<AuditDetail> auditDetails) {
		this.auditDetails = auditDetails;
	}

	public AuditEntity getAuditEntity() {
		return auditEntity;
	}

	public void setAuditEntity(AuditEntity auditEntity) {
		this.auditEntity = auditEntity;
	}

	public AuditType getAuditType() {
		return auditType;
	}

	public void setAuditType(AuditType auditType) {
		this.auditType = auditType;
	}

	public long getEntityId() {
		return this.entityId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	public AuditEventType getEventType() {
		return this.eventType;
	}

	public void setEventType(AuditEventType eventType) {
		this.eventType = eventType;
	}
	public String getUserUID() {
		return userUID;
	}

	public void setUserUID(String userUID) {
		this.userUID = userUID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Audit))
			return false;

		Audit audit = (Audit) o;

		if (getEntityId() != audit.getEntityId())
			return false;
		if (userUID != null ? !userUID.equals(audit.userUID) : audit.userUID != null)
			return false;
		if (getAuditType() != audit.getAuditType())
			return false;
		return getEventType() == audit.getEventType();
	}

	@Override
	public int hashCode() {
		int result = (int) (getEntityId() ^ (getEntityId() >>> 32));
		result = 31 * result + (userUID != null ? userUID.hashCode() : 0);
		result = 31 * result + (getAuditType() != null ? getAuditType().hashCode() : 0);
		result = 31 * result + (getEventType() != null ? getEventType().hashCode() : 0);
		return result;
	}
}
