/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service;

import com.jklprojects.mystory.business.api.entity.AppEntityDAO;
import com.jklprojects.mystory.business.audit.entity.Audit;
import com.jklprojects.mystory.business.audit.entity.AuditDetail;
import com.jklprojects.mystory.business.audit.entity.AuditEventType;
import com.jklprojects.mystory.business.common.AppConfigConsts;
import com.jklprojects.mystory.business.common.util.PasswordHash;
import com.jklprojects.mystory.business.user.entity.ContactUs;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserComm;
import com.jklprojects.mystory.business.user.entity.UserCommAttr;
import com.jklprojects.mystory.business.user.entity.view.ContactUsView;
import com.jklprojects.mystory.business.user.entity.view.UserChangePasswordView;
import com.jklprojects.mystory.business.user.entity.view.UserCommView;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.business.user.entity.view.VerifyAccountView;
import com.jklprojects.mystory.business.user.model.UserMapper;
import com.jklprojects.mystory.business.user.service.api.UserRemote;
import com.jklprojects.mystory.business.user.service.api.UserService;
import com.jklprojects.mystory.business.user.service.rs.rto.UsersRTO;
import com.jklprojects.mystory.common.TimePeriod;
import com.jklprojects.mystory.common.audit.AuditAttrType;
import com.jklprojects.mystory.common.audit.AuditType;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.ex.AppRuntimeException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.user.ContactUsStatus;
import com.jklprojects.mystory.common.user.UserCommAttrName;
import com.jklprojects.mystory.common.user.UserCommName;
import com.jklprojects.mystory.common.user.UserExceptions;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.UserStatus;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.NumUtil;
import com.jklprojects.mystory.common.util.TimeUtil;
import com.jklprojects.mystory.report.user.UsersCreatedReportBean;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * User's CRUD and other services.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-04
 */
@Stateless
public class UserServiceImpl extends AppEntityDAO<User> implements UserService, UserRemote {

	private static final XLogger logger = XLoggerFactory.getXLogger(UserServiceImpl.class);

	private User sysGuestUser;

	public UserServiceImpl() {
		super(User.class);
	}

	@Override
	public ContactUsView createContactUs(ContactUsView contactUs) throws AppCodeException {
		logger.entry(contactUs.getEmail(), contactUs.getFirstName());

		ContactUs contactUsSaved = createEntity(contactUs.setEntityFromView(new ContactUs(), true));

		// send user comm to contact us user
		UserComm userCommContactUsCreated = buildUserCommContactUs(contactUsSaved, getSystemGuestUser().getId(),
				getSystemGuestUser().getUserRoleType(), getEmailTo(contactUsSaved.getEmail()));
		createEntity(userCommContactUsCreated);

		// send user comm to App Admin
		List<User> appAdminUsers = findUsersWithUserRoleTypes(UserRoleType.APP_ADMIN);
		for (User appAdminUser : appAdminUsers) {
			UserComm userCommContactUsCreatedAppAdmin = buildUserCommContactUs(contactUsSaved, appAdminUser.getId(),
					appAdminUser.getUserRoleType(), getEmailTo(appAdminUser.getEmail()));
			createEntity(userCommContactUsCreatedAppAdmin);
		}

		return new ContactUsView().setViewFromEntity(contactUsSaved, true);
	}

	@Override
	public UserCommView createContactUsReply(long contactUsId, String replyText) {
		logger.entry(contactUsId, replyText);

		ContactUs contactUsE = find(contactUsId, ContactUs.class);

		User userAttributedToUserComm = findUserWithEmail(contactUsE.getEmail());
		if (userAttributedToUserComm == null) {
			userAttributedToUserComm = findUserWithUserRoleType(UserRoleType.SYS_USERCOMM);
		}

		contactUsE.setStatus(ContactUsStatus.REPLIED);
		updateEntity(contactUsE);

		UserComm userCommContactUsReply = buildUserCommContactUsReply(contactUsE, getEmailTo(contactUsE.getEmail()),
				userAttributedToUserComm.getId(), userAttributedToUserComm.getUserRoleType(), replyText);
		UserComm userCommE = createEntity(userCommContactUsReply);

		List<User> appAdminUsers = findUsersWithUserRoleTypes(UserRoleType.APP_ADMIN);
		for (User appAdminUser : appAdminUsers) {
			UserComm userCommContactUsReplyCopyAppAdmin = buildUserCommContactUsReply(contactUsE,
					getEmailTo(appAdminUser.getEmail()), appAdminUser.getId(), appAdminUser.getUserRoleType(),
					replyText);
			UserComm userCommAppAdmin = createEntity(userCommContactUsReplyCopyAppAdmin);
		}

		return new UserCommView().setViewFromEntity(userCommE, true);
	}

	@Override
	public UserView createUser(UserView user) throws AppCodeException {
		String userName = user.getUserName();
		String email = user.getEmail();

		User userFound = findUserWithEMailUserNameUserRoleType(email, userName, UserRoleType.APP_USER);
		if (userFound != null) {
			throw UserExceptions.USER_ACCOUNT_010.newAppCodeException(email, userName);
		}

		User userE = createUser(user.setEntityFromView(new User(), true));
		return new UserView().setViewFromEntity(userE, true);
	}

	@Override
	public ContactUsView findContactUs(long contactUsId) {
		return new ContactUsView().setViewFromEntity(find(contactUsId, ContactUs.class), true);
	}

	@Override
	public List<ContactUsView> findContactUsWithStatus(ContactUsStatus... status) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("status", Arrays.asList(status));

		List<ContactUs> entities = find("findContactUsWithStatus", params, ContactUs.class);

		return ContactUsView.toViews(entities.parallelStream().sorted().collect(Collectors.toList()));
	}

	@Override
	public List<UserView> findUserAllOrderByIdDesc() {
		List<User> users = getEM().createNamedQuery("findUserAllOrderByIdDesc", User.class).getResultList();

		return UserView.toViews(users);
	}

	@Override
	public List<User> findUsersCreated(TimePeriod reportPeriod) {
		List<User> usersCreated = Collections.emptyList();

		if (reportPeriod.isYesterday()) {
			LocalDate yesterday = TimeUtil.getYesterday();
			usersCreated = findUsersCreatedBetween(yesterday, yesterday);
		} else if (reportPeriod.isLastWeek()) {
			usersCreated = findUsersCreatedBetween(TimeUtil.getStartOfLastWeek(Locale.getDefault()),
					TimeUtil.getEndOfLastWeek(Locale.getDefault()));
		} else if (reportPeriod.isEpochToLastWeek()) {
			Locale locale = Locale.getDefault();
			usersCreated = findUsersCreatedBetween(TimeUtil.getStartOfEpoch(), TimeUtil.getEndOfLastWeek(locale));
		}

		return usersCreated;
	}

	@Override
	public List<User> findUsersCreatedBetween(LocalDate startPeriod, LocalDate endPeriod) {
		List<User> users = getEM().createNamedQuery("findUsersCreatedBetween", User.class)
				.setParameter("startPeriod", TimeUtil.toOffsetDateTimeStartOfDay(startPeriod))
				.setParameter("endPeriod", TimeUtil.toOffsetDateTimeEndOfDay(endPeriod)).getResultList();
		return users;
	}

	@Override
	public List<User> findUsersCreatedOn(LocalDate createdOnDate) {
		return findUsersCreatedBetween(createdOnDate, createdOnDate);
	}

	@Override
	public List<UsersCreatedReportBean> findUsersCreatedOnReportBeans(LocalDate localDate) {
		return toUsersCreatedReportBeans(findUsersCreatedOn(localDate));
	}

	@Override
	public List<UsersCreatedReportBean> findUsersCreatedReportBeans(TimePeriod reportPeriod) {
		return toUsersCreatedReportBeans(findUsersCreated(reportPeriod));
	}

	@Override
	public List<User> findUsersWithUserRoleTypes(UserRoleType... userRoleTypes) {
		return getEM().createNamedQuery("findUsersWithUserRoleTypes", User.class)
				.setParameter("userRoleTypes", Arrays.asList(userRoleTypes)).getResultList();
	}

	@Override
	public UserView findUserViewWithUserUID(long userUID) throws AppCodeException {
		User user = findUserWithUserUID(userUID);
		return user != null ? new UserView().setViewFromEntity(user, true) : null;
	}

	@Override
	public User findUserWithEmail(String email) {
		return CollUtil.returnFirstOrNull(
				getEM().createNamedQuery("findUserWithEmail", User.class).setParameter("email", email).getResultList());
	}

	/**
	 * Find a user with matching parameters. Return null if a match is not found.
	 *
	 * @param email
	 * @param userName
	 * @param userRoleType
	 * @return user
	 */
	@Override
	public User findUserWithEMailUserNameUserRoleType(String email, String userName, UserRoleType userRoleType) {
		User user = getEM().createNamedQuery("findUserWithEMailUserNameUserRoleType", User.class)
				.setParameter("email", email).setParameter("userName", userName)
				.setParameter("userRoleType", userRoleType).setMaxResults(1).getSingleResult();
		return user;
	}

	@Override
	public User findUserWithUserName(String userName) {
		return getEM().createNamedQuery("findUserWithUserName", User.class).setParameter("userName", userName)
				.setMaxResults(1).getSingleResult();
	}

	@Override
	public User findUserWithUserRoleType(UserRoleType userRoleType) {
		return getEM().createNamedQuery("findUsersWithUserRoleTypes", User.class)
				.setParameter("userRoleTypes", Arrays.asList(userRoleType)).setMaxResults(1).getSingleResult();
	}

	@Override
	public User findUserWithUserUID(long userUID) throws AppCodeException {
		logger.entry(userUID);

		List<User> users = getEM().createNamedQuery("findUserWithUserUID", User.class).setParameter("userUID", userUID)
				.getResultList();
		if (CollUtil.isEmpty(users)) {
			throw UserExceptions.USER_ACCOUNT_011.newAppCodeException(String.valueOf(userUID));
		}
		return CollUtil.returnFirstOrNull(users);
	}

	@Override
	public <V extends UserView> V findUserWithUserUID(long userUID, Class<?> viewClass) throws AppCodeException {
		logger.entry(userUID);
		User user = findUserWithUserUID(userUID);
		return user != null ? toUserView(user, viewClass) : null;
	}

	@Override
	public Locale getUserLocale() {
		return Locale.getDefault();
	}

	@Override
	public User getSystemGuestUser() {
		if (sysGuestUser == null) {
			sysGuestUser = findUserWithUserRoleType(UserRoleType.SYS_GUEST_USER);
		}
		return sysGuestUser;
	}

	@Override
	public boolean isExistingUserEmailUnique(UserView user) {
		return getEM().createNamedQuery("findUserWithEMailUnique", User.class).setParameter("email", user.getEmail())
				.setParameter("id", user.getId()).getResultList().isEmpty();
	}

	@Override
	public boolean isExistingUserUserNameUnique(UserView user) {
		return getEM().createNamedQuery("findUserWithUserNameUnique", User.class)
				.setParameter("userName", user.getUserName()).setParameter("id", user.getId()).getResultList()
				.isEmpty();
	}

	@Override
	public boolean isNewUserEmailUnique(String email) {
		return getEM().createNamedQuery("findUserWithEMail", User.class).setParameter("email", email).getResultList()
				.isEmpty();
	}

	@Override
	public boolean isNewUserUserNameUnique(String userName) {
		logger.entry(userName);
		return getEM().createNamedQuery("findUserWithUserName", User.class).setParameter("userName", userName)
				.getResultList().isEmpty();
	}

	/** {@link #validateCredentials(String, String)} */
	@Override
	public boolean isValidCredentials(String userNameOrEmail, String password) throws AppException {
		return validateCredentials(userNameOrEmail, password).isPresent();
	}

	@Override
	public UserView resetToTempPassword(String email) throws AppCodeException {
		logger.entry(email);

		User user = findUserWithEmail(email);
		UserView userView = new UserView();

		if (user != null) {
			String tempPassword = NumUtil.genRandomToken(USER_TEMP_PASSWORD_SIZE);

			user.setPassword(tempPassword);
			user.setStatus(UserStatus.ACTIVE_TEMP_PWD);
			User userUpdated = update(user);
			userView.setViewFromEntity(userUpdated, false);

			UserComm userCommAccountRecovery = new UserComm(user.getId(), user.getUserRoleType(),
					UserCommName.ACCOUNT_RECOVERY,
					new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT,
							getReportSubject("report_accountRecovery_emailSubject")),
					new UserCommAttr(UserCommAttrName.EMAIL_TO, getEmailTo(user.getEmail())),
					new UserCommAttr(UserCommAttrName.EMAIL, user.getEmail()),
					new UserCommAttr(UserCommAttrName.USERNAME, user.getUserName()),
					new UserCommAttr(UserCommAttrName.ACCOUNT_TEMP_PWD, tempPassword));

			createEntity(userCommAccountRecovery);
		}
		return userView;
	}

	@Override
	public UserView updatePassword(UserChangePasswordView user) throws AppCodeException {
		logger.entry(user.getId(), user.getUserRoleType());

		UserView updatedUserView = new UserView();
		User foundUser = findUserWithUserUID(user.getUid());
		if (foundUser != null) {
			if (isValidCreds(foundUser, user.getCurrentPassword())) {
				if (foundUser.getStatus().isActiveTempPassword()) {
					foundUser.setStatus(UserStatus.ACTIVE);
				}
				foundUser.setPassword(user.getNewPassword());
				User updatedUser = update(foundUser);
				updatedUserView.setViewFromEntity(updatedUser, false);

				UserComm userCommPasswordChanged = new UserComm(user.getId(), user.getUserRoleType(),
						UserCommName.PASSWORD_CHANGED,
						new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT,
								getReportSubject("report_passwordChanged_emailSubject")),
						new UserCommAttr(UserCommAttrName.EMAIL_TO, getEmailTo(user.getEmail())),
						new UserCommAttr(UserCommAttrName.EMAIL, user.getEmail()),
						new UserCommAttr(UserCommAttrName.USERNAME, user.getUserName()));

				createEntity(userCommPasswordChanged);

			} else {
				throw UserExceptions.USER_ACCOUNT_001.newAppCodeException();
			}
		}
		return updatedUserView;
	}

	@Override
	public UserView updateUserAccount(UserView user) throws AppCodeException {

		String newEMail = user.getEmail();

		// find the entity
		User foundUser = find(user.getId());
		String oldEMail = foundUser.getEmail();

		// update the entity from view
		user.setEntityFromView(foundUser, true);
		if (!oldEMail.equalsIgnoreCase(newEMail)) {

			String emailVerifyToken = NumUtil.genRandomToken(USER_EMAIL_VERIFY_TOKEN_SIZE);
			foundUser.setEmailVerifyToken(emailVerifyToken);
			foundUser.setStatus(UserStatus.PENDING_EMAIL_VERIFY);

			foundUser = update(foundUser);

			UserComm userCommVerifyEmailUpdate = new UserComm(foundUser.getId(), foundUser.getUserRoleType(),
					UserCommName.VERIFY_EMAIL_UPDATE,
					new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT,
							getReportSubject("report_verifyEmailUpdate_emailSubject")),
					new UserCommAttr(UserCommAttrName.EMAIL_TO, getEmailTo(foundUser.getEmail())),
					new UserCommAttr(UserCommAttrName.USERNAME, foundUser.getUserName()),
					new UserCommAttr(UserCommAttrName.EMAIL, foundUser.getEmail()),
					new UserCommAttr(UserCommAttrName.EMAIL_OLD, oldEMail),
					new UserCommAttr(UserCommAttrName.EMAIL_VERIFY_TOKEN, foundUser.getEmailVerifyToken()));

			createEntity(userCommVerifyEmailUpdate);
		}

		// return the updated view
		return new UserView().setViewFromEntity(foundUser, true);
	}

	/**
	 * Validate and returns an {@code Optional} with the specified present non-null
	 * value if given credentials are valid, or empty {@code Optional} otherwise.
	 *
	 * @param email
	 * @param password
	 * @return <code>Optional</code> if given credentials are valid, or empty
	 *         otherwise.
	 * @throws AppCodeException
	 * @see AppConfigConsts#USER_MAX_INVALID_SIGNIN_ATTEMPTS
	 * @see AppConfigConsts#USER_ACCOUNT_RECOVERY_DURATION_IN_MINS
	 */
	@Override
	public Optional<UserView> validateCredentials(String email, String password) throws AppCodeException {
		logger.entry(email);

		Optional<UserView> ret = Optional.empty();
		User user = findUserWithEmail(email);
		if (user != null) {
			int maxInvalidSignInAtts = getAppConfigService()
					.getProperty(AppConfigConsts.USER_MAX_INVALID_SIGNIN_ATTEMPTS, 5);
			if (UserStatus.LOCKED_MAX_INVALID_SIGNIN_ATTEMPTS.equals(user.getStatus())) {
				throw UserExceptions.USER_ACCOUNT_005.newAppCodeException(maxInvalidSignInAtts);
			}
			validateTempPasswordExpired(user);

			if (isValidCreds(user, password)) {
				if (user.getInvalidSigninAttempts() > 0) {
					user.resetInvalidSigninAttempts();
					user.setStatus(UserStatus.ACTIVE);
					update(user);
				}
				ret = Optional.of(new UserView().setViewFromEntity(user, true));
			} else {
				// validate sign-in attempts
				user.incrementInvalidSigninAttempts();

				if (user.getInvalidSigninAttempts() >= maxInvalidSignInAtts) {
					user.setStatus(UserStatus.LOCKED_MAX_INVALID_SIGNIN_ATTEMPTS);

					logger.warn("User UID {} locked with maxInvalidSignInAtts {} ", user.getUid(),
							maxInvalidSignInAtts);
					update(user);

					throw UserExceptions.USER_ACCOUNT_005.newAppCodeException(maxInvalidSignInAtts);
				} else {
					update(user);
				}
			}
		}

		return ret;
	}

	@Override
	public boolean verifyAccount(long userUID, String securityToken) throws AppCodeException {
		logger.entry(userUID);

		User user = findUserWithUserUID(userUID);
		User updatedUser = null;

		if (user != null && user.getEmailVerifyToken().equalsIgnoreCase(securityToken)) {
			user.setStatus(UserStatus.ACTIVE);
			updatedUser = update(user);

			UserComm userCommAccountVerified = new UserComm(user.getId(), user.getUserRoleType(),
					UserCommName.ACCOUNT_VERIFIED,
					new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT,
							getReportSubject("report_accountVerified_emailSubject")),
					new UserCommAttr(UserCommAttrName.EMAIL_TO, getEmailTo(user.getEmail())),
					new UserCommAttr(UserCommAttrName.USERNAME, user.getUserName()),
					new UserCommAttr(UserCommAttrName.EMAIL, user.getEmail()));

			createEntity(userCommAccountVerified);
		}

		return updatedUser != null && updatedUser.isIdSet();
	}

	@Override
	public UsersRTO updateUser(UsersRTO updateUsersRTO) throws AppCodeException {

		User foundUser = null;
		User updatedUser = null;
		UsersRTO updatedUsersRTO = null;

		if (updateUsersRTO.isUserTypeAppInternal()) {
			foundUser = findUserWithUserUID(Long.valueOf(updateUsersRTO.getUserUID()));

			if (foundUser != null) {
				UserMapper.getInstance().updateUser(updateUsersRTO, foundUser);

				updatedUser = update(foundUser);

				updatedUsersRTO = UserMapper.getInstance().createUsersRTO(updatedUser);
			}

		} else {
			throw UserExceptions.USER_ACCOUNT_012.newAppCodeException();
		}

		return updatedUsersRTO;
	}

	/**
	 * If the app environment is PROD return email to as is, otherwise return
	 * configured email to for non prod environment.
	 *
	 * @param emailTo
	 * @return
	 */
	public String getEmailTo(final String emailTo) {
		return getAppConfigService().getEMailerBean().getEMailToWithAppEnvName(emailTo);
	}

	private UserComm buildUserCommContactUs(ContactUs contactUsSaved, Long userId, UserRoleType userRoleType,
			String emailTo) {

		UserComm userCommContactUsCreated = new UserComm(userId, userRoleType, UserCommName.CONTACTUS_CREATED,
				new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT,
						I18N.REPORT.getI18N("report_contactUsCreated_emailSubject")),
				new UserCommAttr(UserCommAttrName.EMAIL_TO, emailTo),
				new UserCommAttr(UserCommAttrName.EMAIL, contactUsSaved.getEmail()),
				new UserCommAttr(UserCommAttrName.USERNAME, contactUsSaved.getUserName()),
				new UserCommAttr(UserCommAttrName.EMAIL, contactUsSaved.getEmail()),
				new UserCommAttr(UserCommAttrName.FIRST_NAME, contactUsSaved.getFirstName()),
				new UserCommAttr(UserCommAttrName.LAST_NAME, contactUsSaved.getLastName()),
				new UserCommAttr(UserCommAttrName.CONTACT_US_ID, String.valueOf(contactUsSaved.getId())));

		return userCommContactUsCreated;
	}

	private UserComm buildUserCommContactUsReply(ContactUs contactUsE, String eMailTo, long userId,
			UserRoleType userRoleType, String replyText) {

		UserComm userCommCOntactUsReply = new UserComm(userId, userRoleType, UserCommName.CONTACTUS_REPLY,
				new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT,
						getReportSubject("report_contactUsReply_emailSubject")),
				new UserCommAttr(UserCommAttrName.EMAIL_TO, eMailTo),
				new UserCommAttr(UserCommAttrName.EMAIL, contactUsE.getEmail()),
				new UserCommAttr(UserCommAttrName.USERNAME, contactUsE.getUserName()),
				new UserCommAttr(UserCommAttrName.FIRST_NAME, contactUsE.getFirstName()),
				new UserCommAttr(UserCommAttrName.LAST_NAME, contactUsE.getLastName()),
				new UserCommAttr(UserCommAttrName.CONTACT_US_ID, String.valueOf(contactUsE.getId())),
				new UserCommAttr(UserCommAttrName.CONTACT_US_REPLY_TEXT, replyText));

		return userCommCOntactUsReply;
	}

	@Override
	public List<User> findEntityAll() {
		return getEM().createNamedQuery("findUserAll", User.class).getResultList();
	}

	private User createUser(User user) {
		logger.entry(user.getId(), user.getUid());

		String emailVerifyToken = NumUtil.genRandomToken(USER_EMAIL_VERIFY_TOKEN_SIZE);
		user.setEmailVerifyToken(emailVerifyToken);
		user.setStatus(UserStatus.PENDING_EMAIL_VERIFY);

		User userE = create(user);

		UserComm userCommAccountVerify = new UserComm(userE.getId(), userE.getUserRoleType(),
				UserCommName.ACCOUNT_VERIFY,
				new UserCommAttr(UserCommAttrName.EMAIL_SUBJECT, getReportSubject("report_accountVerify_emailSubject")),
				new UserCommAttr(UserCommAttrName.EMAIL_TO,
						getAppConfigService().getEMailerBean().getEMailToWithAppEnvName(userE.getEmail())),
				new UserCommAttr(UserCommAttrName.USERNAME, userE.getUserName()),
				new UserCommAttr(UserCommAttrName.EMAIL, userE.getEmail()),
				new UserCommAttr(UserCommAttrName.EMAIL_VERIFY_TOKEN, userE.getEmailVerifyToken()));

		createEntity(userCommAccountVerify);

		logger.info("User created. id {}, UID {}", userE.getId(), userE.getUid());
		logger.info("User Comm created. id {}, user comm name {} ", userCommAccountVerify.getId(),
				userCommAccountVerify.getUserCommName());

		logger.exit();

		return userE;
	}

	private String getReportSubject(String key) {
		return new StringBuilder(getAppConfigService().getAppName()).append(" ").append(I18N.REPORT.getI18N(key))
				.toString();
	}

	/**
	 * Return true if user's password match, false otherwise.
	 *
	 * @param user
	 * @param password
	 * @return if true user's password match, false otherwise.
	 */
	private boolean isValidCreds(User user, String password) {
		try {
			boolean validCreds = PasswordHash.validatePasswordWithSalt(password, user.getSalt(), user.getHash(),
					PasswordHash.PBKDF2_ITERATIONS);

			Audit audit = new Audit(user, AuditType.USER_AUTHENTICATION, AuditEventType.CREATE,
					String.valueOf(user.getUid()));
			audit.addAuditDetail(
					new AuditDetail(AuditAttrType.USER_AUTH_SUCCESS.getAttrName(), Boolean.toString(validCreds)));

			getAuditService().createAudit(audit);

			return validCreds;

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new AppRuntimeException(e.getMessage(), e);
		}
	}

	private List<UsersCreatedReportBean> toUsersCreatedReportBeans(List<User> users) {
		List<UsersCreatedReportBean> usersCreatedReportBeanList = new ArrayList<>();
		for (User user : users) {
			usersCreatedReportBeanList
					.add(new UsersCreatedReportBean(user.getUserName(), user.getFirstName(), user.getLastName(),
							String.valueOf(user.getUid()), user.getUserRoleType().getI18N(), user.getStatus().getI18N(),
							user.getCreatedTimestampFormatted(), user.getUpdatedTimestampFormatted()));
		}
		return usersCreatedReportBeanList;
	}

	@SuppressWarnings("unchecked")
	private <V extends UserView> V toUserView(User user, Class<?> viewClass) {
		if (viewClass.isAssignableFrom(UserView.class)) {
			return (V) new UserView().setViewFromEntity(user, true);
		} else if (viewClass.isAssignableFrom(UserChangePasswordView.class)) {
			return (V) new UserChangePasswordView().setViewFromEntity(user, true);
		} else if (viewClass.isAssignableFrom(VerifyAccountView.class)) {
			return (V) new VerifyAccountView().setViewFromEntity(user, true);
		} else {
			logger.error("View Class {} not supported, returning null", viewClass.getCanonicalName());
			return null;
		}
	}

	/**
	 * @param user
	 * @throws AppCodeException
	 *             if account recovery duration expired
	 * @see AppConfigConsts#USER_ACCOUNT_RECOVERY_DURATION_IN_MINS
	 */
	private void validateTempPasswordExpired(User user) throws AppCodeException {
		if (user.getStatus().isActiveTempPassword()) {
			int pwdResetDurationInMins = getAppConfigService()
					.getProperty(AppConfigConsts.USER_ACCOUNT_RECOVERY_DURATION_IN_MINS, 5);

			if (user.getCreatedUpdated().getUpdatedTimestamp() != null) {
				Duration durationDiff = Duration.between(user.getCreatedUpdated().getUpdatedTimestamp(),
						OffsetDateTime.now());

				logger.debug("durationDiff mins {} ", durationDiff.toMinutes());
				logger.debug("durationDiff getSeconds {} ", durationDiff.getSeconds());

				if (durationDiff.getSeconds() > Duration.ofMinutes(pwdResetDurationInMins).getSeconds()) {
					throw UserExceptions.USER_ACCOUNT_006.newAppCodeException(pwdResetDurationInMins);
				}
			}
		}
	}
}
