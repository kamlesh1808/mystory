/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service.api;

import com.jklprojects.mystory.business.user.UserConsts;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.view.ContactUsView;
import com.jklprojects.mystory.business.user.entity.view.UserChangePasswordView;
import com.jklprojects.mystory.business.user.entity.view.UserCommView;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.business.user.service.rs.rto.UsersRTO;
import com.jklprojects.mystory.common.TimePeriod;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.user.ContactUsStatus;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.report.user.UsersCreatedReportBean;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.ejb.Local;

/**
 * EJB Local interface for User's CRUD and other services.
 *
 * @author Kamleshkumar Patel
 * @version 1, 2015-04-04
 * @version 1, 2016-12-15
 * @version 1, 2017-10-23
 * @version 1, 2017-11-23
 * @version 2, 2018-03-23
 * @version 2, 2018-04-23
 * @version 2, 2018-05-23
 * @version 3, 2018-12-23
 * @version 4, 2019-03-23
 */
@Local
public interface UserService extends UserConsts {

	ContactUsView createContactUs(ContactUsView contactUs) throws AppCodeException;

	UserCommView createContactUsReply(long contactUsId, String replyText);

	UserView createUser(UserView user) throws AppCodeException;

	ContactUsView findContactUs(long contactUsId);

	List<ContactUsView> findContactUsWithStatus(ContactUsStatus... status);

	List<User> findEntityAll();

	List<User> findEntityAll(String orderByColumnName, String order);

	List<UserView> findUserAllOrderByIdDesc();

	List<User> findUsersCreated(TimePeriod reportPeriod);

	List<User> findUsersCreatedBetween(LocalDate startPeriod, LocalDate endPeriod);

	List<User> findUsersCreatedOn(LocalDate localDate);

	List<UsersCreatedReportBean> findUsersCreatedOnReportBeans(LocalDate createdOnDate);

	List<UsersCreatedReportBean> findUsersCreatedReportBeans(TimePeriod reportPeriod);

	List<User> findUsersWithUserRoleTypes(UserRoleType... userRoleTypes);

	UserView findUserViewWithUserUID(long userUID) throws AppCodeException;

	User findUserWithEmail(String email);

	User findUserWithEMailUserNameUserRoleType(String email, String userName, UserRoleType userRoleType);

	User findUserWithUserName(String userName);

	User findUserWithUserRoleType(UserRoleType userRoleType);

	/**
	 * Find user with user UID.
	 *
	 * @param userUID
	 * @return user with user UID or null if not found
	 */
	User findUserWithUserUID(long userUID) throws AppCodeException;

	<T extends UserView> T findUserWithUserUID(long userUID, Class<?> viewClass) throws AppCodeException;

	Locale getUserLocale();

	User getSystemGuestUser();

	boolean isExistingUserEmailUnique(UserView user);

	boolean isExistingUserUserNameUnique(UserView user);

	boolean isNewUserEmailUnique(String email);

	boolean isNewUserUserNameUnique(String username);

	boolean isValidCredentials(String userNameOrEmail, String password) throws AppException;

	UserView resetToTempPassword(String email) throws AppCodeException;

	UserView updatePassword(UserChangePasswordView user) throws AppCodeException;

	UserView updateUserAccount(UserView user) throws AppCodeException;

	Optional<UserView> validateCredentials(String userNameOrEmail, String password) throws AppCodeException;

	boolean verifyAccount(long userUID, String securityToken) throws AppCodeException;

	UsersRTO updateUser(UsersRTO usersRTO) throws AppCodeException;
}
