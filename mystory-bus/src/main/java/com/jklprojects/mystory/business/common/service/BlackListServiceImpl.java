/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.service;

import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.business.common.entity.BlackList;
import com.jklprojects.mystory.business.common.entity.BlackListView;
import com.jklprojects.mystory.business.common.service.api.BlackListService;
import com.jklprojects.mystory.common.blacklist.BlackListType;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.StringUtil;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * BlackList CRUD and other services.
 *
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-06-23
 * @since 2.18.06.23
 */
@Stateless
public class BlackListServiceImpl extends AppEntityDAO<BlackList> implements BlackListService {

	private static final XLogger logger = XLoggerFactory.getXLogger(BlackListServiceImpl.class);

	public BlackListServiceImpl() {
		super(BlackList.class);
	}

	@Override
	public List<BlackListView> findBlackListWithBlackListType(BlackListType blackListType) {
		logger.entry(blackListType);

		List<BlackList> entities = getEM().createNamedQuery("findBlackListWithBlackListType", BlackList.class)
				.setParameter("blackListType", blackListType).getResultList();

		return BlackListView.toViews(entities);
	}

	@Override
	public boolean isBlackListed(String value, List<String> blackList) {
		if (StringUtil.isNotEmpty(value) && CollUtil.isNotEmpty(blackList)) {
			return blackList.parallelStream().filter(blackListValue -> blackListValue.equalsIgnoreCase(value))
					.findFirst().isPresent();
		}
		return false;
	}

	@Override
	public boolean isBlackListed(String value, BlackListType blackListType) {
		List<BlackListView> blackList = findBlackListWithBlackListType(blackListType);
		if (CollUtil.isNotEmpty(blackList)) {
			List<String> blackListValue = blackList.parallelStream().map(BlackListView::getBlackListValue)
					.collect(Collectors.toList());

			return isBlackListed(value, blackListValue);
		}
		return false;
	}
}
