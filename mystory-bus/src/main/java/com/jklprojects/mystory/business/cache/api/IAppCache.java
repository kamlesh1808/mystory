/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.cache.api;

import com.jklprojects.mystory.business.common.entity.table.CountryView;
import java.util.List;
import javax.ejb.Local;

/**
 * This API provides access to the cached entities.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2019-06-23
 */
@Local
public interface IAppCache {

	List<CountryView> getCountries();
}
