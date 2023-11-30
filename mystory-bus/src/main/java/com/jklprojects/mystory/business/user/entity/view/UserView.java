/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.story.entity.view.StoryReplyView;
import com.jklprojects.mystory.business.user.entity.User;
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
 * Represents light weight view for User entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-04-15
 */
@XmlRootElement
public class UserView extends AbstractAppView<User> implements Comparable<UserView> {
	private static final long serialVersionUID = 8002071703640628L;

	private static final XLogger logger = XLoggerFactory.getXLogger(UserView.class);

	/**
	 * User Unique Identifier
	 *
	 * @since 2.18.04.23
	 */
	private Long uid;

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private UserStatus status;
	private String emailVerifyToken;
	private int invalidSigninAttempts;
	// private Set<StoryView> stories = new HashSet<>();
	private UserRoleType userRoleType;
	private final List<StoryReplyView> storyReplies = new ArrayList<>();
	private AddressView addressHome = new AddressView();
	private String aboutMe;

	public UserView() {
		super(User.class);
	}

	public UserView(Long uid) {
		this();

		setUid(uid);
	}

	public static List<UserView> toViews(List<User> entities) {
		List<UserView> views = new ArrayList<>();
		for (User entity : entities) {
			views.add(new UserView().setViewFromEntity(entity, true));
		}
		return views;
	}

	public static List<UserView> toViewsSorted(List<User> entities) {
		List<UserView> views = toViews(entities);
		Collections.sort(views);
		return views;
	}

	@Override
	public int compareTo(UserView o) {
		return Comparator.comparing(UserView::getUserName).thenComparing(UserView::getFirstName)
				.thenComparing(UserView::getLastName).compare(this, o);
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public AddressView getAddressHome() {
		return addressHome;
	}

	public void setAddressHome(AddressView addressHome) {
		this.addressHome = addressHome;
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

	public int getInvalidSigninAttempts() {
		return invalidSigninAttempts;
	}

	public void setInvalidSigninAttempts(int count) {
		this.invalidSigninAttempts = count;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public List<StoryReplyView> getStoryReplies() {
		return storyReplies;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
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
		return getUid() != null ? getUid().hashCode() : 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserView))
			return false;

		UserView userView = (UserView) o;

		return getUid() != null ? getUid().equals(userView.getUid()) : userView.getUid() == null;
	}

	@Override
	public User setEntityFromView(User entity, boolean setBi) throws AppCodeException {

		entity.setUid(getUid());
		entity.setFirstName(getFirstName());
		entity.setLastName(getLastName());
		entity.setUserName(getUserName());
		entity.setPassword(getPassword());
		entity.setEmail(getEmail());
		entity.setUserRoleType(getUserRoleType());
		entity.setAboutMe(getAboutMe());

		if (getStatus() != null) {
			entity.setStatus(getStatus());
		}

		entity.setAddressHome(getAddressHome().setEntityFromView(entity.getAddressHome(), false));
		return entity;
	}

	@Override
	public String toString() {
		return "UserView{" + "uid=" + uid + ", userName='" + userName + '\'' + ", status=" + status + ", userRoleType="
				+ userRoleType + "} " + super.toString();
	}

	@Override
	public UserView setViewFromEntity(User entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		setUid(entity.getUid());
		setFirstName(entity.getFirstName());
		setLastName(entity.getLastName());
		setUserName(entity.getUserName());
		setEmail(entity.getEmail());
		setEmailVerifyToken(entity.getEmailVerifyToken());
		setStatus(entity.getStatus());
		setInvalidSigninAttempts(entity.getInvalidSigninAttempts());
		setUserRoleType(entity.getUserRoleType());
		setAboutMe(entity.getAboutMe());

		if (setBi) {
			if (entity.getAddressHome() != null) {
				setAddressHome(new AddressView().setViewFromEntity(entity.getAddressHome(), false));
			}
		}

		return this;
	}
}
