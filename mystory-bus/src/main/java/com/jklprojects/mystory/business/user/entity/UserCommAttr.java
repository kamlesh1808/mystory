/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.user.entity.converter.UserCommAttrNameAttrConverter;
import com.jklprojects.mystory.common.user.UserCommAttrName;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents User Communication Attribute entity which form a content of the
 * user communication.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-11-27
 */
@Entity
@Table(name = "S_USER_COMM_ATTR")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class UserCommAttr extends AbstractAppEntity {

	private static final long serialVersionUID = 7505312125200175734L;

	private static final XLogger logger = XLoggerFactory.getXLogger(UserCommAttr.class);

	@Column(name = "attr_id")
	@Convert(converter = UserCommAttrNameAttrConverter.class)
	private UserCommAttrName attrName;

	@Column(name = "value")
	private String value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_comm_id", nullable = false)
	private UserComm userComm;

	public UserCommAttr() {
		super();
	}

	public UserCommAttr(UserCommAttrName attrName, String value) {
		super();

		this.setAttrName(attrName);
		this.setValue(value);
	}

	public UserCommAttrName getAttrName() {
		return attrName;
	}

	public void setAttrName(UserCommAttrName attrName) {
		this.attrName = attrName;
	}

	public String getValue() {
		return value;
	}

	public void setUserComm(UserComm userComm) {
		this.userComm = userComm;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserCommAttr that = (UserCommAttr) o;

		if (getAttrName() != that.getAttrName())
			return false;
		return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
	}

	@Override
	public int hashCode() {
		int result = getAttrName() != null ? getAttrName().hashCode() : 0;
		result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "UserCommAttr{" + "attrName=" + attrName + ", value='" + value + '\'' + "} " + super.toString();
	}
}
