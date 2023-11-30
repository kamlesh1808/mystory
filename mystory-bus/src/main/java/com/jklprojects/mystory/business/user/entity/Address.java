/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.common.entity.table.Country;
import com.jklprojects.mystory.business.user.entity.converter.AddressStatusAttrConverter;
import com.jklprojects.mystory.business.user.entity.converter.AddressTypeAttrConverter;
import com.jklprojects.mystory.common.user.AddressStatus;
import com.jklprojects.mystory.common.user.AddressType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Address entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, July 4, 2015
 */
@Entity
@Table(name = "A_ADDRESS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Address extends AbstractAppEntity {
	private static final long serialVersionUID = 6170423029131084407L;

	@Column(name = "line1")
	private String line1;

	@Column(name = "line2")
	private String line2;

	@Column(name = "city")
	private String city;

	@Column(name = "state_prov")
	private String stateProv;

	@OneToOne(optional = false)
	@JoinColumn(name = "country", nullable = false)
	private Country country;

	@Column(name = "postal_code_zip")
	private String postalCodeZip;

	@Column(name = "type")
	@Convert(converter = AddressTypeAttrConverter.class)
	private AddressType addressType;

	@Column(name = "status")
	@Convert(converter = AddressStatusAttrConverter.class)
	private AddressStatus status;

	/** public no-arg constructor */
	public Address() {
		super();

		status = AddressStatus.ACTIVE;
	}

	public Address(AddressType addressType) {
		this();
		this.addressType = addressType;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProv() {
		return stateProv;
	}

	public void setStateProv(String stateProv) {
		this.stateProv = stateProv;
	}

	public Country getCountry() {
		return country != null ? country : new Country();
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getPostalCodeZip() {
		return postalCodeZip;
	}

	public void setPostalCodeZip(String postalCodeZip) {
		this.postalCodeZip = postalCodeZip;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public AddressStatus getStatus() {
		return status;
	}

	public void setStatus(AddressStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressType == null) ? 0 : addressType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Address))
			return false;
		Address other = (Address) obj;
		return addressType == other.addressType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [getLine1()=").append(getLine1()).append(", getLine2()=").append(getLine2())
				.append(", getCity()=").append(getCity()).append(", getStateProv()=").append(getStateProv())
				.append(", getCountry()=").append(getCountry()).append(", getPostalCodeZip()=")
				.append(getPostalCodeZip()).append(", getAddressType()=").append(getAddressType())
				.append(", getStatus()=").append(getStatus()).append(", toString()=").append(super.toString())
				.append("]");
		return builder.toString();
	}
}
