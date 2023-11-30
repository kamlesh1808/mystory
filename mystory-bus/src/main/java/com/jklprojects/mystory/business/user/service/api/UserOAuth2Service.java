/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service.api;

import com.jklprojects.mystory.business.api.auth.GUserInfo;
import com.jklprojects.mystory.business.user.UserConsts;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.common.TimePeriod;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.report.user.UsersCreatedReportBean;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Local;

/**
 * UserOAuth2 CRUD and other services.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.03.23
 */
@Local
public interface UserOAuth2Service extends UserConsts {

	/**
	 * Create Google {@link UserOAuth2}
	 *
	 * @param gUserInfo
	 * @return
	 */
	UserOAuth2 createUserOAuth2(GUserInfo gUserInfo);

	/**
	 * Find all {@code UserOAuth2} users.
	 *
	 * @return
	 */
	List<UserOAuth2View> findUserOAuth2AllOrderByIdDesc();

	UserOAuth2View findUserOAuthViewWithOAuthUID(String oAuthUID) throws AppCodeException;

	/**
	 * Find a {@link UserOAuth2} with OAuth2 UID. Return null if a match is not
	 * found.
	 *
	 * @param oAuthUID
	 * @return
	 */
	UserOAuth2 findUserOAuthWithOAuthUID(String oAuthUID) throws AppCodeException;

	/**
	 * Find a {@link UserOAuth2} with OAuth2 UID and user role type parameters.
	 * Return null if a match is not found.
	 *
	 * @param oAuthUID
	 * @param userRoleType
	 * @return userOAuth2
	 */
	UserOAuth2 findUserOAuthWithOAuthUIDUserRoleType(String oAuthUID, UserRoleType userRoleType)
			throws AppCodeException;

	/**
	 * Find {@code UserOAuth2} users created for the report period.
	 *
	 * @param reportPeriod
	 * @return
	 */
	List<UserOAuth2> findUsersOAuth2Created(TimePeriod reportPeriod);

	/**
	 * Find {@code UserOAuth2} users created between the start and the end period.
	 *
	 * @param startPeriod
	 * @param endPeriod
	 * @return
	 */
	List<UserOAuth2> findUsersOAuth2CreatedBetween(LocalDate startPeriod, LocalDate endPeriod);

	List<UsersCreatedReportBean> findUsersOAuth2CreatedReportBeans(TimePeriod reportPeriod);

	/**
	 * Update account info related to OAuth2 user.
	 *
	 * @param userOAuth2
	 * @return
	 * @throws AppCodeException
	 */
	UserOAuth2View updateUserOAuth2Account(UserOAuth2View userOAuth2) throws AppCodeException;
}
