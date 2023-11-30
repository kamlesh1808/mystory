/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.service.api;

import com.jklprojects.mystory.business.common.entity.BlackListView;
import com.jklprojects.mystory.common.blacklist.BlackListType;
import java.util.List;
import javax.ejb.Local;

/**
 * EJB Local interface for BlackList CRUD and other services.
 *
 * @author Kamleshkumar Patel
 * @version 2, 2018-06-23
 * @since 2.18.06.23
 */
@Local
public interface BlackListService {

	List<BlackListView> findBlackListWithBlackListType(BlackListType blackListType);

	boolean isBlackListed(String value, BlackListType blackListType);

	boolean isBlackListed(String value, List<String> blackList);
}
