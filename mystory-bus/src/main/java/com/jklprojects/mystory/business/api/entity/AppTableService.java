/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import com.jklprojects.mystory.business.common.entity.table.CountryView;
import java.util.List;
import javax.ejb.Local;

/**
 * An application wide singleton bean to access application tables.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, July 5, 2015
 */
@Local
public interface AppTableService {
	List<CountryView> getCountries();
}
