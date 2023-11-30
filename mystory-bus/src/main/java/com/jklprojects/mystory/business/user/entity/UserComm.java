/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.user.entity.converter.UserCommNameAttrConverter;
import com.jklprojects.mystory.business.user.entity.converter.UserCommStatusAttrConverter;
import com.jklprojects.mystory.common.user.UserCommName;
import com.jklprojects.mystory.common.user.UserCommStatus;
import com.jklprojects.mystory.common.user.UserRoleType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * User Communication entity. Used to store communication details which will be
 * sent to user at an automatic scheduled interval.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-11-27
 */
@Entity
@Table(name = "S_USER_COMM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({@NamedQuery(name = "findUserComms", query = "SELECT uc " + "FROM UserComm uc "
		+ "WHERE uc.status IN :status " + "ORDER BY uc.createdUpdated.createdTimestamp ASC")})
public class UserComm extends AbstractAppEntity {

	private static final long serialVersionUID = -1350225797162923614L;

	private static final XLogger logger = XLoggerFactory.getXLogger(UserComm.class);

	@Column(name = "comm_name_id")
	@Convert(converter = UserCommNameAttrConverter.class)
	private UserCommName userCommName;

	@Column(name = "status")
	@Convert(converter = UserCommStatusAttrConverter.class)
	private UserCommStatus status;

	@Column(name = "user_id", nullable = true)
	private Long userId;

	@Column(name = "user_oauth2_id", nullable = true)
	private Long userOAuthId;

	@OneToMany(mappedBy = "userComm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private final List<UserCommAttr> attrs = new ArrayList<>();

	public UserComm() {
		super();

		status = UserCommStatus.NEW;
	}

	/**
	 * @param userCommName
	 * @param attrs
	 */
	public UserComm(UserCommName userCommName, List<UserCommAttr> attrs) {
		this();

		this.userCommName = userCommName;

		for (UserCommAttr uct : attrs) {
			addAttr(uct);
		}
	}

	/**
	 * @param userId
	 * @param userCommName
	 * @param userRoleType
	 * @param attrs
	 */
	public UserComm(long userId, UserRoleType userRoleType, UserCommName userCommName, UserCommAttr... attrs) {
		this(userCommName, Arrays.asList(attrs));

		if (userRoleType.getUserType().isAppInternal()) {
			this.userId = userId;
		} else if (userRoleType.getUserType().isOAuth2External()) {
			this.userOAuthId = userId;
		}
	}

	/**
	 * @param userId
	 * @param userCommName
	 * @param userRoleType
	 * @param attrs
	 */
	public UserComm(long userId, UserRoleType userRoleType, UserCommName userCommName, List<UserCommAttr> attrs) {
		this(userCommName, attrs);

		if (userRoleType.getUserType().isAppInternal()) {
			this.userId = userId;
		} else if (userRoleType.getUserType().isOAuth2External()) {
			this.userOAuthId = userId;
		}
	}

	@Override
	public String toString() {
		return "UserComm{" + "userCommName=" + userCommName + ", status=" + status + ", userId=" + userId
				+ ", userOAuthId=" + userOAuthId + ", attrs=" + attrs + "} " + super.toString();
	}

	public void addAttr(UserCommAttr attr) {
		if (!attrs.contains(attr)) {
			attrs.add(attr);
			attr.setUserComm(this);
		}
	}

	public void removeAttr(UserCommAttr attr) {
		attrs.remove(attr);
		attr.setUserComm(null);
	}

	public List<UserCommAttr> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<UserCommAttr> attrs) {
		for (UserCommAttr attr : attrs) {
			addAttr(attr);
		}
	}

	public UserCommStatus getStatus() {
		return status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserComm userComm = (UserComm) o;

		if (getUserCommName() != userComm.getUserCommName())
			return false;
		if (getStatus() != userComm.getStatus())
			return false;
		if (getUserId() != null ? !getUserId().equals(userComm.getUserId()) : userComm.getUserId() != null)
			return false;
		return getUserOAuthId() != null
				? getUserOAuthId().equals(userComm.getUserOAuthId())
				: userComm.getUserOAuthId() == null;
	}

	@Override
	public int hashCode() {
		int result = getUserCommName() != null ? getUserCommName().hashCode() : 0;
		result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
		result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
		result = 31 * result + (getUserOAuthId() != null ? getUserOAuthId().hashCode() : 0);
		return result;
	}

	public void setStatus(UserCommStatus status) {
		this.status = status;
	}

	public UserCommName getUserCommName() {
		return userCommName;
	}

	public void setUserCommName(UserCommName userCommName) {
		this.userCommName = userCommName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserOAuthId() {
		return userOAuthId;
	}

	public void setUserOAuthId(Long userOAuthId) {
		this.userOAuthId = userOAuthId;
	}
}
