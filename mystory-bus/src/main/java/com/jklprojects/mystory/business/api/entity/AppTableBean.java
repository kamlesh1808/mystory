/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import com.jklprojects.mystory.business.common.entity.table.Country;
import com.jklprojects.mystory.business.common.entity.table.CountryView;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * An application wide singleton bean to access application tables.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-07-05
 * @version 4, 2019-06-23
 */
@Singleton
@Startup
@AccessTimeout(value = 500, unit = TimeUnit.MILLISECONDS)
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Lock(LockType.READ)
public class AppTableBean extends AbstractGenericDAO implements AppTableService {

	private static final XLogger logger = XLoggerFactory.getXLogger(AppTableBean.class);

	public AppTableBean() {
		super();
	}

	@Override
	@Lock(LockType.READ)
	public List<CountryView> getCountries() {
		logger.debug("get countries");
		List<Country> entities = findAll(Country.class);
		return CountryView.toViews(entities.parallelStream().sorted().collect(Collectors.toList()));
	}
}
