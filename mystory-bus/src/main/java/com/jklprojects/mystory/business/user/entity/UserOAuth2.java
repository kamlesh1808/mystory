/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.audit.Auditable;
import com.jklprojects.mystory.business.audit.entity.AuditEntity;
import com.jklprojects.mystory.business.audit.entity.AuditEntityListener;
import com.jklprojects.mystory.business.audit.entity.AuditEventType;
import com.jklprojects.mystory.business.user.entity.converter.UserRoleTypeAttrConverter;
import com.jklprojects.mystory.business.user.entity.converter.UserStatusAttrConverter;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.UserStatus;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * UserOAuth2 entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.03.23
 */
@Entity
@Table(name = "S_USER_OAUTH2")
@NamedQueries({@NamedQuery(name = "findUserOAuth2All", query = "SELECT ua FROM UserOAuth2 ua"),
		@NamedQuery(name = "findUserOAuth2WithOAuth2UIDUserRoleType", query = "SELECT ua FROM UserOAuth2 ua "
				+ "WHERE ua.idOAuth2 = :oAuth2UID AND ua.userRoleType = :userRoleType"),
		@NamedQuery(name = "findUserOAuth2WithOAuthUID", query = "SELECT ua FROM UserOAuth2 ua WHERE ua.idOAuth2 = :oAuthUID"),
		@NamedQuery(name = "findUsersOAuth2CreatedBetween", query = "SELECT ua FROM UserOAuth2 ua "
				+ "WHERE ua.createdUpdated.createdTimestamp BETWEEN :startPeriod AND :endPeriod"),
		@NamedQuery(name = "findUserOAuth2AllOrderByIdDesc", query = "SELECT ua FROM UserOAuth2 ua ORDER BY ua.id DESC")})
@Auditable(name = AuditEntity.USER_OAUTH2, auditEventType = {AuditEventType.CREATE, AuditEventType.PRE_UPDATE,
		AuditEventType.POST_UPDATE, AuditEventType.DELETE})
@EntityListeners(AuditEntityListener.class)
public class UserOAuth2 extends AbstractAppEntity {

	private static final long serialVersionUID = -5139535054286897175L;

	private static final XLogger logger = XLoggerFactory.getXLogger(UserOAuth2.class);

	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "id_oauth2")
	private String idOAuth2;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "status")
	@Convert(converter = UserStatusAttrConverter.class)
	private UserStatus status;

	@Column(name = "user_role_type")
	@Convert(converter = UserRoleTypeAttrConverter.class)
	private UserRoleType userRoleType;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "locale")
	private String locale;

	@Column(name = "picture_url")
	private String pictureURL;

	@Column(name = "link")
	private String link;

	@Column(name = "about_me")
	private String aboutMe;

	public UserOAuth2() {
		super();
	}

	@Override
	public int hashCode() {
		return getIdOAuth2().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserOAuth2 that = (UserOAuth2) o;

		return getIdOAuth2().equals(that.getIdOAuth2());
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserOAuth2{" + "idOAuth2='" + idOAuth2 + '\'' + ", status=" + status + ", userRoleType=" + userRoleType
				+ "} " + super.toString();
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getIdOAuth2() {
		return idOAuth2;
	}

	public void setIdOAuth2(String idOAuth2) {
		this.idOAuth2 = idOAuth2;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public UserStatus getStatus() {
		return this.status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserRoleType getUserRoleType() {
		return this.userRoleType;
	}

	public void setUserRoleType(UserRoleType userRoleType) {
		this.userRoleType = userRoleType;
	}
}
