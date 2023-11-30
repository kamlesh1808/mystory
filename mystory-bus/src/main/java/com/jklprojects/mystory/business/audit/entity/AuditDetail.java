/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.entity;

import com.jklprojects.mystory.business.api.entity.AbstractIdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-10-06
 */
@Entity
@Table(name = "A_AUDIT_DETAIL")
public class AuditDetail extends AbstractIdEntity {
	private static final long serialVersionUID = -8525868339223595700L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "a_audit_id", nullable = false)
	private Audit audit;

	@Column(name = "attr_name")
	private String attrName;

	@Column(name = "value")
	private String value;

	public AuditDetail() {
		super();
	}

	public AuditDetail(String attrName, String value) {
		super();

		this.attrName = attrName;
		this.value = value;
	}

	public Audit getAudit() {
		return this.audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AuditDetail{" + "attrName='" + attrName + '\'' + ", value='" + value + '\'' + "} " + super.toString();
	}

	@Override
	public int hashCode() {
		int result = getAttrName() != null ? getAttrName().hashCode() : 0;
		result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AuditDetail that = (AuditDetail) o;

		if (getAttrName() != null ? !getAttrName().equals(that.getAttrName()) : that.getAttrName() != null)
			return false;
		return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
	}
}
