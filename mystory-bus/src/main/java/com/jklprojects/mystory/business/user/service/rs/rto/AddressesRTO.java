/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service.rs.rto;

import com.jklprojects.mystory.business.api.rs.rto.RTO;
import com.jklprojects.mystory.business.user.entity.view.AddressView;
import com.jklprojects.mystory.common.user.AddressType;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Address immutable RTO.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-04-23
 * @version 2, 2018-04-23
 */
public class AddressesRTO implements RTO {

	private static final long serialVersionUID = -7736851919818340961L;

	private static final XLogger logger = XLoggerFactory.getXLogger(AddressesRTO.class);

	private String line1;
	private String line2;
	private String city;
	private String stateProv;
	private String country;
	private String postalCodeZip;
	private AddressType type;

	/** public no-arg constructor */
	public AddressesRTO() {
		super();
	}

	public AddressesRTO(final AddressView view) {
		this();

		line1 = view.getLine1();
		line2 = view.getLine2();
		city = view.getCity();
		stateProv = view.getStateProv();
		country = view.getCountry().getAlpha2Code();
		postalCodeZip = view.getPostalCodeZip();
		type = view.getType();
	}

	/** @return the line1 */
	public String getLine1() {
		return line1;
	}

	/** @return the line2 */
	public String getLine2() {
		return line2;
	}

	/** @return the city */
	public String getCity() {
		return city;
	}

	/** @return the stateProv */
	public String getStateProv() {
		return stateProv;
	}

	/** @return the country */
	public String getCountry() {
		return country;
	}

	/** @return the postalCodeZip */
	public String getPostalCodeZip() {
		return postalCodeZip;
	}

	/** @return the type */
	public AddressType getType() {
		return type;
	}
}
