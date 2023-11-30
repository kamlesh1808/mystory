/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.cache.api.IAppCache;
import com.jklprojects.mystory.business.common.entity.table.CountryView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-07-23
 * @version 1, 2017-01-23
 * @version 4, 2019-03-23
 * @version 4, 2019-06-23
 */
@Named
@SessionScoped
public class CountryDataSource implements Serializable {
	private static final long serialVersionUID = -7167212066802660671L;

	@Inject
	private @Named("appCacheJ") IAppCache iAppCache;

	public CountryDataSource() {
		super();
	}

	public List<CountryView> getCountries() {
		return iAppCache.getCountries();
	}

	public List<CountryView> queryByName(final String queryStr) {
		List<CountryView> queried = new ArrayList<>();
		String queryStrLower = queryStr.toLowerCase();

		for (CountryView country : getCountries()) {
			String countryNameLower = country.getNameEn().toLowerCase();
			String countryAlpha2Code = country.getAlpha2Code().toLowerCase();
			if (countryNameLower.startsWith(queryStrLower) || countryAlpha2Code.equals(queryStrLower)) {
				queried.add(country);
			}
		}

		return queried.parallelStream().sorted().collect(Collectors.toList());
	}
}
