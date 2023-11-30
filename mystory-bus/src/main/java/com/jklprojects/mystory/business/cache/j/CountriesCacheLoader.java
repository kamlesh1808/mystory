/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.cache.j;

import com.jklprojects.mystory.business.api.entity.AppTableService;
import com.jklprojects.mystory.business.common.entity.table.CountryView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * JCache Cache Loader for {@link CountryView}
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2019-06-23
 */
public class CountriesCacheLoader implements CacheLoader<String, List<CountryView>> {
	private static final XLogger logger = XLoggerFactory.getXLogger(CountriesCacheLoader.class);

	private static final long serialVersionUID = -426303800491058062L;

	private final AppTableService appTableService;

	public CountriesCacheLoader(AppTableService appTableService) {
		this.appTableService = appTableService;
	}

	@Override
	public List<CountryView> load(String key) throws CacheLoaderException {
		logger.debug("load");
		return appTableService.getCountries();
	}

	@Override
	public Map<String, List<CountryView>> loadAll(Iterable<? extends String> keys) throws CacheLoaderException {
		logger.debug("loadAll");

		Map<String, List<CountryView>> map = new HashMap<>();
		map.put("countries", appTableService.getCountries());

		return map;
	}
}
