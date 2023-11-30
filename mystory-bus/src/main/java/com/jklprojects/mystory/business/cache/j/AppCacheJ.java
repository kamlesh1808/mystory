/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.cache.j;

import com.jklprojects.mystory.business.api.entity.AppTableService;
import com.jklprojects.mystory.business.cache.api.IAppCache;
import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.business.common.entity.table.CountryView;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * JCache implementation of access to the Appplication's cached entities
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2019-06-23
 */
@Singleton
@Startup
@Named("appCacheJ")
public class AppCacheJ implements IAppCache {

	private static final XLogger logger = XLoggerFactory.getXLogger(AppCacheJ.class);

	private final CachingProvider provider = Caching.getCachingProvider();
	private final CacheManager cacheManager = provider.getCacheManager();

	private Cache<String, List<CountryView>> countriesCache;

	@Inject
	private AppTableService appTableService;

	@Inject
	private AppConfigService appConfigService;

	public AppCacheJ() {
		super();
	}

	@PostConstruct
	private void initCountriesCacheJ() {
		int duration = appConfigService.getWriteQAccessExpireInSeconds();
		logger.debug("init countries cache j with duration: {}", duration);

		MutableConfiguration<String, List<CountryView>> configCountries = new MutableConfiguration<String, List<CountryView>>()
				.setStoreByValue(false).setReadThrough(true)
				.setCacheLoaderFactory(new FactoryBuilder.SingletonFactory<>(new CountriesCacheLoader(appTableService)))
				.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, duration)));

		countriesCache = cacheManager.createCache("countries", configCountries);
	}

	@Override
	public List<CountryView> getCountries() {
		logger.debug("get countries");

		return countriesCache.get("countries");
	}
}
