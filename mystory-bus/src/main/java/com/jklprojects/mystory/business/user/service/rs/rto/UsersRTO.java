/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service.rs.rto;

import com.jklprojects.mystory.business.api.rs.rto.LinkRTO;
import com.jklprojects.mystory.business.common.rs.AbstractRTO;
import com.jklprojects.mystory.business.story.service.rs.rto.StoriesRTO;
import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.common.story.dto.StoryLinkDTO;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.UserStatus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.HttpMethod;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User immutable RTO.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2017-04-23
 */
@XmlRootElement(name = "User")
public class UsersRTO extends AbstractRTO {

	public static final String RELATIONSHIP_TYPE = "users";
	private static final long serialVersionUID = -5030140407703078033L;
	private long id;
	private String userUID;

	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private UserStatus status;
	private UserRoleType userRoleType;
	private int invalidSigninAttempts;
	private String createdTimestamp;
	private String updatedTimestamp;
	private AddressesRTO addressHome;
	private final List<LinkRTO> links = new ArrayList<LinkRTO>();

	/** public no-arg constructor */
	public UsersRTO() {
		super();
	}

	public UsersRTO(String restAPIBaseURI) {
		super(restAPIBaseURI);
	}

	public UsersRTO(final UserOAuth2View userOAuth2View, String restAPIBaseURI) {
		this(restAPIBaseURI);

		if (userOAuth2View != null) {
			userUID = userOAuth2View.getIdOAuth2();
			id = userOAuth2View.getId();
			firstName = userOAuth2View.getFirstName();
			lastName = userOAuth2View.getLastName();
			userName = userOAuth2View.getUserName();
			email = userOAuth2View.getEmail();
			status = userOAuth2View.getStatus();
			invalidSigninAttempts = 0;
			userRoleType = userOAuth2View.getUserRoleType();

			createdTimestamp = userOAuth2View.getCreatedTimestampFormatted();
			updatedTimestamp = userOAuth2View.getCreatedTimestampFormatted();
		}
	}

	public UsersRTO(final UserView userView, String restAPIBaseURI) {
		this(restAPIBaseURI);

		if (userView != null) {
			userUID = String.valueOf(userView.getUid());
			id = userView.getId();
			firstName = userView.getFirstName();
			lastName = userView.getLastName();
			userName = userView.getUserName();
			email = userView.getEmail();
			status = userView.getStatus();
			invalidSigninAttempts = userView.getInvalidSigninAttempts();
			userRoleType = userView.getUserRoleType();
			addressHome = new AddressesRTO(userView.getAddressHome());

			createdTimestamp = userView.getCreatedTimestampFormatted();
			updatedTimestamp = userView.getCreatedTimestampFormatted();
		}
	}

	/** @return the addressHome */
	public AddressesRTO getAddressHome() {
		return addressHome;
	}

	/** @return the createdTimestamp */
	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	/** @return the email */
	public String getEmail() {
		return email;
	}

	/** @return the firstName */
	public String getFirstName() {
		return firstName;
	}

	public long getId() {
		return id;
	}

	/** @return the invalidSigninAttempts */
	public int getInvalidSigninAttempts() {
		return invalidSigninAttempts;
	}

	/** @return the lastName */
	public String getLastName() {
		return lastName;
	}

	public List<LinkRTO> getLinks() {
		return links;
	}

	/** @return the status */
	public UserStatus getStatus() {
		return status;
	}

	/** @return the updatedTimestamp */
	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	/** @return the userName */
	public String getUserName() {
		return userName;
	}

	/** @return the userRoleType */
	public UserRoleType getUserRoleType() {
		return userRoleType;
	}

	public boolean isUserTypeAppInternal() {
		return userRoleType.getUserType().isAppInternal();
	}

	public String getUserUID() {
		return userUID;
	}

	@Override
	public int hashCode() {
		int result = (int) (getId() ^ (getId() >>> 32));
		result = 31 * result + (getUserUID() != null ? getUserUID().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UsersRTO))
			return false;

		UsersRTO usersRTO = (UsersRTO) o;

		if (getId() != usersRTO.getId())
			return false;
		return getUserUID() != null ? getUserUID().equals(usersRTO.getUserUID()) : usersRTO.getUserUID() == null;
	}

	@Override
	public String toString() {
		return "UsersRTO{" + "id=" + id + ", userUID='" + userUID + '\'' + ", userName='" + userName + '\''
				+ ", status=" + status + ", userRoleType=" + userRoleType + ", createdTimestamp='" + createdTimestamp
				+ '\'' + ", updatedTimestamp='" + updatedTimestamp + '\'' + "} " + super.toString();
	}

	public void buildStoriesLinks(List<StoryLinkDTO> stories) {
		for (StoryLinkDTO storyLinkDTO : stories) {
			String href = getRestAPIBaseURI() + StoriesRTO.RELATIONSHIP_TYPE + "/" + storyLinkDTO.getStoryId();
			links.add(new LinkRTO(String.valueOf(storyLinkDTO.getStoryId()), href, StoriesRTO.RELATIONSHIP_TYPE,
					HttpMethod.GET));
		}
		Collections.sort(links);
	}
}
