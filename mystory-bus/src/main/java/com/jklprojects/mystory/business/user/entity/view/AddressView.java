/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.common.entity.table.Country;
import com.jklprojects.mystory.business.common.entity.table.CountryView;
import com.jklprojects.mystory.business.user.entity.Address;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.user.AddressType;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents light weight view for Address entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, July 4, 2015
 */
public class AddressView extends AbstractAppView<Address> {

	private static final long serialVersionUID = 879939184118757090L;

	private static final XLogger logger = XLoggerFactory.getXLogger(AddressView.class);

	private String line1;
	private String line2;
	private String city;
	private String stateProv;
	private CountryView country = new CountryView();
	private String postalCodeZip;
	private AddressType type;

	public AddressView() {
		super(Address.class);
	}

	public AddressView(AddressType addressType) {
		this();
		setType(addressType);
	}

	@Override
	public Address setEntityFromView(Address entity, boolean setBi) throws AppCodeException {
		logger.entry(this);

		if (getCountry() != null) {
			entity.setCountry(getCountry().setEntityFromView(new Country(), false));
		}
		return entity;
	}

	@Override
	public AddressView setViewFromEntity(Address entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		logger.entry(entity);

		setLine1(entity.getLine1());
		setLine2(entity.getLine2());
		setCity(entity.getCity());
		setStateProv(entity.getStateProv());
		setPostalCodeZip(entity.getPostalCodeZip());
		setType(entity.getAddressType());

		setCountry(new CountryView().setViewFromEntity(entity.getCountry(), false));

		return this;
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

	public CountryView getCountry() {
		return country;
	}

	public void setCountry(CountryView country) {
		this.country = country;
	}

	public String getPostalCodeZip() {
		return postalCodeZip;
	}

	public void setPostalCodeZip(String postalCodeZip) {
		this.postalCodeZip = postalCodeZip;
	}

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AddressView))
			return false;

		AddressView that = (AddressView) o;

		if (getLine1() != null ? !getLine1().equals(that.getLine1()) : that.getLine1() != null)
			return false;
		if (getLine2() != null ? !getLine2().equals(that.getLine2()) : that.getLine2() != null)
			return false;
		if (getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null)
			return false;
		if (getStateProv() != null ? !getStateProv().equals(that.getStateProv()) : that.getStateProv() != null)
			return false;
		if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null)
			return false;
		if (getPostalCodeZip() != null
				? !getPostalCodeZip().equals(that.getPostalCodeZip())
				: that.getPostalCodeZip() != null)
			return false;
		return getType() == that.getType();
	}

	@Override
	public int hashCode() {
		int result = getLine1() != null ? getLine1().hashCode() : 0;
		result = 31 * result + (getLine2() != null ? getLine2().hashCode() : 0);
		result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
		result = 31 * result + (getStateProv() != null ? getStateProv().hashCode() : 0);
		result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
		result = 31 * result + (getPostalCodeZip() != null ? getPostalCodeZip().hashCode() : 0);
		result = 31 * result + (getType() != null ? getType().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "AddressView{" + "line1='" + line1 + '\'' + ", line2='" + line2 + '\'' + ", city='" + city + '\''
				+ ", stateProv='" + stateProv + '\'' + ", country=" + country + ", postalCodeZip='" + postalCodeZip
				+ '\'' + ", type=" + type + "} " + super.toString();
	}
}
