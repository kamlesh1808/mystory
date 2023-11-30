/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.converter;

import com.jklprojects.mystory.business.common.entity.table.CountryView;
import com.jklprojects.mystory.web.user.bean.CountryDataSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 * @see http://stackoverflow.com/a/7665768 for @ManagedProperty issue
 *      in @FacesConverter
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-07-05
 * @version 4, 2019-03-23
 */
@javax.faces.bean.ManagedBean(name = "countryACConverter")
public class CountryACConverter implements Converter<CountryView> {

	/*
	 * The use of pre-JEE 8 @javax.faces.bean.ManagedBean(name =
	 * "countryACConverter") is required for the converter to work
	 */

	@Inject
	private CountryDataSource countryDS;

	public CountryACConverter() {
		super();
	}

	@Override
	public CountryView getAsObject(FacesContext context, UIComponent component, String value) {
		if (value != null && value.trim().length() > 0) {
			for (CountryView country : getCountryDS().getCountries()) {
				if (country.getNameEn().equals(value)) {
					return country;
				}
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, CountryView country) {
		return country.getNameEn();
	}

	public CountryDataSource getCountryDS() {
		return countryDS;
	}

	public void setCountryDS(CountryDataSource countryDS) {
		this.countryDS = countryDS;
	}
}
