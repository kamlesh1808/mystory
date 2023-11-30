/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.entity.table;

import com.jklprojects.mystory.business.api.entity.AbstractTableEntity;
import com.jklprojects.mystory.business.common.entity.converter.TableStatusAttrConverter;
import com.jklprojects.mystory.common.TableStatus;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * CountryView entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, July 5, 2015
 */
@Entity
@Table(name = "T_COUNTRY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Country extends AbstractTableEntity implements Comparable<Country> {

	private static final long serialVersionUID = 6833082877964464831L;

	@Column(name = "name_en")
	private String nameEn;

	@Id
	@Column(name = "alpha2_code")
	private String alpha2Code;

	@Column(name = "alpha3_code")
	private String alpha3Code;

	@Column(name = "numeric_code")
	private Integer numericCode;

	@Column(name = "dialing_code")
	private String dialingCode;

	@Column(name = "local_name")
	private String localName;

	@Column(name = "status")
	@Convert(converter = TableStatusAttrConverter.class)
	private TableStatus status;

	/** public no-arg constructor */
	public Country() {
		super();

		status = TableStatus.ACTIVE;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getAlpha2Code() {
		return alpha2Code;
	}

	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}

	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}

	public Integer getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(Integer numericCode) {
		this.numericCode = numericCode;
	}

	public String getDialingCode() {
		return dialingCode;
	}

	public void setDialingCode(String dialingCode) {
		this.dialingCode = dialingCode;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public TableStatus getStatus() {
		return status;
	}

	public void setStatus(TableStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Country))
			return false;

		Country country = (Country) o;

		if (getNameEn() != null ? !getNameEn().equals(country.getNameEn()) : country.getNameEn() != null)
			return false;
		if (getAlpha2Code() != null
				? !getAlpha2Code().equals(country.getAlpha2Code())
				: country.getAlpha2Code() != null)
			return false;
		if (getAlpha3Code() != null
				? !getAlpha3Code().equals(country.getAlpha3Code())
				: country.getAlpha3Code() != null)
			return false;
		if (getNumericCode() != null
				? !getNumericCode().equals(country.getNumericCode())
				: country.getNumericCode() != null)
			return false;
		return getDialingCode() != null
				? getDialingCode().equals(country.getDialingCode())
				: country.getDialingCode() == null;
	}

	@Override
	public int hashCode() {
		int result = getNameEn() != null ? getNameEn().hashCode() : 0;
		result = 31 * result + (getAlpha2Code() != null ? getAlpha2Code().hashCode() : 0);
		result = 31 * result + (getAlpha3Code() != null ? getAlpha3Code().hashCode() : 0);
		result = 31 * result + (getNumericCode() != null ? getNumericCode().hashCode() : 0);
		result = 31 * result + (getDialingCode() != null ? getDialingCode().hashCode() : 0);
		return result;
	}

	/**
	 * Compare using name
	 *
	 * @param o
	 * @return
	 * @see String#compareTo(String)
	 */
	@Override
	public int compareTo(Country o) {
		return getNameEn() != null && o != null ? getNameEn().compareTo(o.getNameEn()) : 0;
	}

	@Override
	public String toString() {
		return "Country [getNameEn()=" + getNameEn() + ", getAlpha2Code()=" + getAlpha2Code() + ", getAlpha3Code()="
				+ getAlpha3Code() + ", getNumericCode()=" + getNumericCode() + ", getDialingCode()=" + getDialingCode()
				+ ", getLocalName()=" + getLocalName() + ", getStatus()=" + getStatus() + "]";
	}
}
