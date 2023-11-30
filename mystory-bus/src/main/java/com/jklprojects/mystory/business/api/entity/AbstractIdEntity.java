/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * A @MappedSuperclass entity whose properties will be inherited by the sub
 * classes. Entities needing @Id should inherit from this entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, May 1, 2015
 */
@MappedSuperclass
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractIdEntity implements Serializable {
	private static final long serialVersionUID = -7009519849932841582L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	public AbstractIdEntity() {
		super();
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "AbstractIdEntity{" + "id=" + id + '}';
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isIdSet() {
		return getId() > 0;
	}
}
