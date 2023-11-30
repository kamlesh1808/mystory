/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service;

import com.jklprojects.mystory.business.api.auth.GUserInfo;
import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.service.api.UserOAuth2Service;
import com.jklprojects.mystory.common.TimePeriod;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.user.UserExceptions;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.UserStatus;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.TimeUtil;
import com.jklprojects.mystory.report.user.UsersCreatedReportBean;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * UserOAuth2 CRUD and other services.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.03.23
 */
@Stateless
public class UserOAuth2ServiceImpl extends AppEntityDAO<UserOAuth2> implements UserOAuth2Service {

	private static final XLogger logger = XLoggerFactory.getXLogger(UserOAuth2ServiceImpl.class);

	public UserOAuth2ServiceImpl() {
		super(UserOAuth2.class);
	}

	@Override
	public UserOAuth2 createUserOAuth2(GUserInfo gUserInfo) {
		UserOAuth2 userOAuth2 = new UserOAuth2();

		logger.info("Creating UserOAuth2 OAuth2UID: {} ");

		userOAuth2.setIdOAuth2(gUserInfo.getId());
		userOAuth2.setUserName(gUserInfo.getUserName());
		userOAuth2.setEmail(gUserInfo.getEmail());
		userOAuth2.setFirstName(gUserInfo.getGiven_name());
		userOAuth2.setLastName(gUserInfo.getFamily_name());
		userOAuth2.setUserRoleType(UserRoleType.GOOGLE);
		userOAuth2.setStatus(UserStatus.ACTIVE);
		userOAuth2.setLocale(gUserInfo.getLocale());
		userOAuth2.setPictureURL(gUserInfo.getPicture());
		userOAuth2.setLink(gUserInfo.getLink());

		logger.info("Creating UserOAuth2: oAuth2UID {}, userRoleType ", userOAuth2.getIdOAuth2(),
				userOAuth2.getUserRoleType());

		return create(userOAuth2);
	}

	@Override
	public List<UserOAuth2View> findUserOAuth2AllOrderByIdDesc() {
		List<UserOAuth2> userOAuth2List = getEM().createNamedQuery("findUserOAuth2AllOrderByIdDesc", UserOAuth2.class)
				.getResultList();

		return UserOAuth2View.toViews(userOAuth2List);
	}

	@Override
	public UserOAuth2View findUserOAuthViewWithOAuthUID(String oAuthUID) throws AppCodeException {
		UserOAuth2View userOAuth2View = null;
		UserOAuth2 userOAuth2 = findUserOAuthWithOAuthUID(oAuthUID);
		if (userOAuth2 != null) {
			userOAuth2View = new UserOAuth2View().setViewFromEntity(userOAuth2, true);
		}
		return userOAuth2View;
	}

	@Override
	public UserOAuth2 findUserOAuthWithOAuthUID(String oAuthUID) throws AppCodeException {
		logger.entry(oAuthUID);
		List<UserOAuth2> userOAuth2List = getEM().createNamedQuery("findUserOAuth2WithOAuthUID", UserOAuth2.class)
				.setParameter("oAuthUID", oAuthUID).getResultList();

		if (CollUtil.isEmpty(userOAuth2List)) {
			throw UserExceptions.USER_ACCOUNT_011.newAppCodeException(oAuthUID);
		}

		return CollUtil.returnFirstOrNull(userOAuth2List);
	}

	@Override
	public UserOAuth2 findUserOAuthWithOAuthUIDUserRoleType(String oAuthUID, UserRoleType userRoleType)
			throws AppCodeException {

		logger.info("oAuthUID: {}, userRoleType: {}", oAuthUID, userRoleType);

		List<UserOAuth2> userOAuth2List = getEM()
				.createNamedQuery("findUserOAuth2WithOAuth2UIDUserRoleType", UserOAuth2.class)
				.setParameter("oAuth2UID", oAuthUID).setParameter("userRoleType", userRoleType).getResultList();
		//
		// if (CollUtil.isEmpty(userOAuth2List)) {
		// throw UserExceptions.USER_ACCOUNT_011.newAppCodeException(oAuthUID);
		// }

		return CollUtil.returnFirstOrNull(userOAuth2List);
	}

	@Override
	public List<UserOAuth2> findUsersOAuth2Created(TimePeriod reportPeriod) {
		List<UserOAuth2> usersCreated = Collections.emptyList();

		if (reportPeriod.isYesterday()) {
			LocalDate yesterday = TimeUtil.getYesterday();
			usersCreated = findUsersOAuth2CreatedBetween(yesterday, yesterday);
		} else if (reportPeriod.isLastWeek()) {
			usersCreated = findUsersOAuth2CreatedBetween(TimeUtil.getStartOfLastWeek(Locale.getDefault()),
					TimeUtil.getEndOfLastWeek(Locale.getDefault()));
		} else if (reportPeriod.isEpochToLastWeek()) {
			Locale locale = Locale.getDefault();
			usersCreated = findUsersOAuth2CreatedBetween(TimeUtil.getStartOfEpoch(), TimeUtil.getEndOfLastWeek(locale));
		}

		return usersCreated;
	}

	@Override
	public List<UserOAuth2> findUsersOAuth2CreatedBetween(LocalDate startPeriod, LocalDate endPeriod) {
		List<UserOAuth2> userOAuth2s = getEM().createNamedQuery("findUsersOAuth2CreatedBetween", UserOAuth2.class)
				.setParameter("startPeriod", TimeUtil.toOffsetDateTimeStartOfDay(startPeriod))
				.setParameter("endPeriod", TimeUtil.toOffsetDateTimeEndOfDay(endPeriod)).getResultList();

		return userOAuth2s;
	}

	@Override
	public List<UsersCreatedReportBean> findUsersOAuth2CreatedReportBeans(TimePeriod reportPeriod) {
		return toUsersOAuth2CreatedReportBeans(findUsersOAuth2Created(reportPeriod));
	}

	@Override
	public UserOAuth2View updateUserOAuth2Account(UserOAuth2View userOAuth2) throws AppCodeException {
		UserOAuth2 updatedUserOAuth2 = updateWithView(userOAuth2);

		return new UserOAuth2View().setViewFromEntity(updatedUserOAuth2, true);
	}

	private List<UsersCreatedReportBean> toUsersOAuth2CreatedReportBeans(List<UserOAuth2> users) {
		List<UsersCreatedReportBean> usersCreatedReportBeanList = new ArrayList<>();
		for (UserOAuth2 userOAuth2 : users) {
			usersCreatedReportBeanList.add(new UsersCreatedReportBean(userOAuth2.getUserName(),
					userOAuth2.getFirstName(), userOAuth2.getLastName(), userOAuth2.getIdOAuth2(),
					userOAuth2.getUserRoleType().getI18N(), userOAuth2.getStatus().getI18N(),
					userOAuth2.getCreatedTimestampFormatted(), userOAuth2.getUpdatedTimestampFormatted()));
		}
		return usersCreatedReportBeanList;
	}
}
