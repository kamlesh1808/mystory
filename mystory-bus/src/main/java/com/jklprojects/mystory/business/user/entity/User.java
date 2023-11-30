/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.audit.Auditable;
import com.jklprojects.mystory.business.audit.entity.AuditEntity;
import com.jklprojects.mystory.business.audit.entity.AuditEntityListener;
import com.jklprojects.mystory.business.audit.entity.AuditEventType;
import com.jklprojects.mystory.business.common.util.PasswordHash;
import com.jklprojects.mystory.business.user.entity.converter.UserRoleTypeAttrConverter;
import com.jklprojects.mystory.business.user.entity.converter.UserStatusAttrConverter;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.user.AddressType;
import com.jklprojects.mystory.common.user.UserExceptions;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.UserStatus;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * User entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-01
 */
@Entity
@Table(name = "S_USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Auditable(name = AuditEntity.USER, auditEventType = {AuditEventType.CREATE, AuditEventType.PRE_UPDATE,
		AuditEventType.POST_UPDATE, AuditEventType.DELETE})
@EntityListeners(AuditEntityListener.class)
@NamedQueries({@NamedQuery(name = "findUserAll", query = "SELECT u FROM User u ORDER by u.userName ASC"),
		@NamedQuery(name = "findUserAllOrderByIdDesc", query = "SELECT u FROM User u ORDER by u.id DESC"),
		@NamedQuery(name = "findUserWithUserNameOrEmail", query = "SELECT u FROM User u WHERE u.userName = :userName OR u.email= :email"),
		@NamedQuery(name = "findUserWithEmail", query = "SELECT u FROM User u WHERE u.email= :email"),
		@NamedQuery(name = "findUserWithUserUID", query = "SELECT u FROM User u WHERE u.uid = :userUID"),
		@NamedQuery(name = "findUserWithUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
		@NamedQuery(name = "findUserWithUserNameUnique", query = "SELECT u FROM User u WHERE u.userName = :userName AND u.id <> :id"),
		@NamedQuery(name = "findUserWithEMail", query = "SELECT u FROM User u WHERE u.email = :email"),
		@NamedQuery(name = "findUserWithEMailUserNameUserRoleType", query = "SELECT u FROM User u WHERE u.email = :email "
				+ "AND u.userName = :userName AND u.userRoleType = :userRoleType"),
		@NamedQuery(name = "findUserWithEMailUnique", query = "SELECT u FROM User u WHERE u.email = :email AND u.id <> :id"),
		@NamedQuery(name = "findUsersCreatedBetween", query = "SELECT u FROM User u "
				+ "WHERE u.createdUpdated.createdTimestamp BETWEEN :startPeriod AND :endPeriod"),
		@NamedQuery(name = "findUsersWithUserRoleTypes", query = "SELECT u FROM User u WHERE u.userRoleType IN :userRoleTypes")})
public class User extends AbstractAppEntity {
	private static final long serialVersionUID = 1938883284462790573L;

	private static final XLogger logger = XLoggerFactory.getXLogger(User.class);

	/**
	 * User Unique Identifier
	 *
	 * @since 2.18.04.23
	 */
	@Column(name = "uid")
	private Long uid;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "username")
	private String userName;

	@Column(name = "email")
	private String email;

	@Auditable(auditValue = false)
	@Column(name = "salt")
	private String salt;

	@Column(name = "hash")
	private String hash;

	@Column(name = "status")
	@Convert(converter = UserStatusAttrConverter.class)
	private UserStatus status;

	@Column(name = "email_verify_token")
	private String emailVerifyToken;

	/** not persistence transient field */
	private transient String password;

	@Column(name = "invalid_signin_attempts")
	private int invalidSigninAttempts;

	@Column(name = "user_role_type")
	@Convert(converter = UserRoleTypeAttrConverter.class)
	private UserRoleType userRoleType;

	@Column(name = "about_me")
	private String aboutMe;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "address_id", nullable = false)
	private Address addressHome;

	public User() {
		super();

		status = UserStatus.NEW;
	}

	public User(UserRoleType userRoleType) {
		this();

		setUserRoleType(userRoleType);
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public Address getAddressHome() {
		return addressHome != null ? addressHome : new Address(AddressType.HOME);
	}

	public void setAddressHome(Address addressHome) {
		this.addressHome = addressHome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailVerifyToken() {
		return emailVerifyToken;
	}

	public void setEmailVerifyToken(String emailVerifyToken) {
		this.emailVerifyToken = emailVerifyToken;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public int getInvalidSigninAttempts() {
		return invalidSigninAttempts;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * Set password with salt and hash.
	 *
	 * @param password
	 * @throws AppCodeException
	 */
	public void setPassword(String password) throws AppCodeException {
		if (password != null) {
			this.password = password;

			String pwdHash;
			try {
				pwdHash = PasswordHash.createHash(this.getPassword(), PasswordHash.PBKDF2_ITERATIONS);
				String[] params = pwdHash.split(":");
				setSalt(params[PasswordHash.SALT_INDEX]);
				setHash(params[PasswordHash.PBKDF2_INDEX]);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {

				logger.catching(e);

				throw logger.throwing(UserExceptions.USER_ACCOUNT_007.newAppCodeException());
			}
		}
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUIDAsString() {
		return String.valueOf(uid);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserRoleType getUserRoleType() {
		return userRoleType;
	}

	public void setUserRoleType(UserRoleType userRoleType) {
		this.userRoleType = userRoleType;
	}

	@Override
	public int hashCode() {
		return getUid().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		com.jklprojects.mystory.business.user.entity.User user = (com.jklprojects.mystory.business.user.entity.User) o;

		return getUid().equals(user.getUid());
	}

	public void incrementInvalidSigninAttempts() {
		++invalidSigninAttempts;
	}

	public void resetInvalidSigninAttempts() {
		invalidSigninAttempts = 0;
	}

	@Override
	public String toString() {
		return "User{" + "uid=" + uid + ", userName='" + userName + '\'' + ", status=" + status + ", userRoleType="
				+ userRoleType + "} " + super.toString();
	}
}
