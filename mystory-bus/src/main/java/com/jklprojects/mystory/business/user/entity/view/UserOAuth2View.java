/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.UserStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents light weight view for UserOAuth2 entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.04.23
 */
@XmlRootElement
public class UserOAuth2View extends AbstractAppView<UserOAuth2> implements Comparable<UserOAuth2View> {

	private static final long serialVersionUID = 7736906570864361140L;

	private static final XLogger logger = XLoggerFactory.getXLogger(UserOAuth2.class);

	/**
	 * User Unique Identifier
	 *
	 * @since 2.18.04.23
	 */
	private String idOAuth2;

	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private UserStatus status;
	private UserRoleType userRoleType;
	private String aboutMe;

	// private Set<StoryView> stories = new HashSet<>();

	public UserOAuth2View() {
		super(UserOAuth2.class);
	}

	public static List<UserOAuth2View> toViews(List<UserOAuth2> entities) {
		List<UserOAuth2View> views = new ArrayList<>();
		for (UserOAuth2 entity : entities) {
			views.add(new UserOAuth2View().setViewFromEntity(entity, true));
		}
		return views;
	}

	public static List<UserOAuth2View> toViewsSorted(List<UserOAuth2> entities) {
		List<UserOAuth2View> views = toViews(entities);
		Collections.sort(views);
		return views;
	}

	@Override
	public int compareTo(UserOAuth2View o) {
		return Comparator.comparing(UserOAuth2View::getUserName).thenComparing(UserOAuth2View::getFirstName)
				.thenComparing(UserOAuth2View::getLastName).compare(this, o);
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getAuthorName() {
		return (getFirstName() + " " + getLastName());
	}

	public String getAuthorURL() {
		return (getFirstName() + "-" + getLastName()).toLowerCase();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
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
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
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
		return getIdOAuth2() != null ? getIdOAuth2().hashCode() : 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserOAuth2View))
			return false;

		UserOAuth2View that = (UserOAuth2View) o;

		return getIdOAuth2() != null ? getIdOAuth2().equals(that.getIdOAuth2()) : that.getIdOAuth2() == null;
	}

	@Override
	public UserOAuth2 setEntityFromView(UserOAuth2 entity, boolean setBi) throws AppCodeException {
		entity.setIdOAuth2(entity.getIdOAuth2());
		entity.setFirstName(getFirstName());
		entity.setLastName(getLastName());
		entity.setUserName(getUserName());
		entity.setEmail(getEmail());
		entity.setUserRoleType(getUserRoleType());
		entity.setAboutMe(getAboutMe());

		if (getStatus() != null) {
			entity.setStatus(getStatus());
		}

		if (setBi) {
		}

		return entity;
	}

	@Override
	public String toString() {
		return "UserOAuth2View{" + "idOAuth2='" + idOAuth2 + '\'' + ", status=" + status + ", userRoleType="
				+ userRoleType + "} " + super.toString();
	}

	@Override
	public UserOAuth2View setViewFromEntity(UserOAuth2 entity, boolean setBi) {
		super.setViewFromEntity(entity, setBi);

		setIdOAuth2(entity.getIdOAuth2());
		setFirstName(entity.getFirstName());
		setLastName(entity.getLastName());
		setUserName(entity.getUserName());
		setEmail(entity.getEmail());
		setStatus(entity.getStatus());
		setUserRoleType(entity.getUserRoleType());
		setAboutMe(entity.getAboutMe());

		if (setBi) {
		}

		return this;
	}
}
