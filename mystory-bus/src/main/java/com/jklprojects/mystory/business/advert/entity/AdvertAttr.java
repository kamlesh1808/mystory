/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.common.advert.AdvertAttrType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2016-12-31
 */
@Entity
@Table(name = "F_ADVERT_ATTR")
@NamedQuery(name = "findAdvertAttrAll", query = "SELECT a FROM AdvertAttr a")
public class AdvertAttr extends AbstractAppEntity {

	private static final long serialVersionUID = 9146459694442525692L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_advert_id")
	private Advert advert;

	@Column(name = "attr_id")
	private AdvertAttrType advertAttrType;

	@Column(name = "value")
	private String value;

	public AdvertAttr() {
		super();
	}

	public Advert getAdvert() {
		return advert;
	}

	public void setAdvert(Advert advert) {
		this.advert = advert;
	}

	public AdvertAttrType getAdvertAttrType() {
		return advertAttrType;
	}

	public void setAdvertAttrType(AdvertAttrType advertAttrType) {
		this.advertAttrType = advertAttrType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
