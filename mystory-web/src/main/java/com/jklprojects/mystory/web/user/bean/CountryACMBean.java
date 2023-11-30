/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.common.entity.table.CountryView;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-05-05
 * @version 1, 2017-01-23
 * @version 4, 2019-03-23
 */
@Named
@RequestScoped
public class CountryACMBean {
	@Inject
	private CountryDataSource countryDS;

	private List<CountryView> countries;

	public CountryACMBean() {
		super();
	}

	public List<CountryView> complete(String query) {
		return countryDS.queryByName(query);
	}

	public List<CountryView> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryView> countries) {
		this.countries = countries;
	}

	public CountryDataSource getCountryDS() {
		return countryDS;
	}

	public void setCountryDS(CountryDataSource countryDS) {
		this.countryDS = countryDS;
	}
}
