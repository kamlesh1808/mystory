/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service.rs;

import com.jklprojects.mystory.business.api.rs.AbstractRESTService;
import com.jklprojects.mystory.business.story.service.api.StoryService;
import com.jklprojects.mystory.business.story.service.rs.StoriesRS;
import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.business.user.service.api.UserOAuth2Service;
import com.jklprojects.mystory.business.user.service.api.UserService;
import com.jklprojects.mystory.business.user.service.rs.rto.UsersRTO;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.user.UserExceptions;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2017-04-23
 */
@Stateless
@Path("/" + UsersRTO.RELATIONSHIP_TYPE)
public class UsersRS extends AbstractRESTService {

	private static final XLogger logger = XLoggerFactory.getXLogger(StoriesRS.class);

	@Inject
	UserOAuth2Service userOAuth2Service;

	@Inject
	private UserService userService;

	@Inject
	private StoryService storyService;

	public UsersRS() {
		super();
	}

	public UserOAuth2Service getUserOAuth2Service() {
		return userOAuth2Service;
	}

	public UserService getUserService() {
		return userService;
	}

	public StoryService getStoryService() {
		return storyService;
	}

	@PUT
	@Path("/useruid/{useruid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("useruid") long userUID, UsersRTO updateUsersRTO) {

		try {
			UsersRTO updatedUsersRTO = getUserService().updateUser(updateUsersRTO);
			updateUsersRTO.setRestAPIBaseURI(getRESTAPIBaseURI());
			return Response.status(Response.Status.OK).entity(updatedUsersRTO).build();

		} catch (AppCodeException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getI18NMessage()).build();
		}
	}

	@Path("/username/{username}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findUserWithUsername(@PathParam("username") String userName, @Context HttpHeaders headers) {
		logger.entry(userName);

		if (!isRESTRequestAuthorized(headers)) {
			return Response.status(Status.FORBIDDEN).build();
		}

		User user = getUserService().findUserWithUserName(userName);
		if (user == null) {
			return Response.status(Status.NOT_FOUND)
					.entity(UserExceptions.USER_ACCOUNT_008.newAppCodeException(userName).getI18NMessage()).build();
		}

		UsersRTO usersRTO = new UsersRTO(new UserView().setViewFromEntity(user, true), getRESTAPIBaseURI());
		usersRTO.setAppEnvName(getAppConfigService().getAppEnvName());

		return Response.status(Status.OK).entity(usersRTO).build();
	}

	@Path("/useruid/{useruid}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findUserWithUserUID(@PathParam("useruid") long userUID, @Context HttpHeaders headers) {
		logger.entry(userUID);

		if (!isRESTRequestAuthorized(headers)) {
			return Response.status(Status.FORBIDDEN).build();
		}

		User user = null;
		try {
			user = getUserService().findUserWithUserUID(userUID);
		} catch (AppCodeException e) {
			logger.warn(e.getI18NMessage());

			return Response.status(Status.NOT_FOUND).entity(e.getI18NMessage()).build();
		}

		UsersRTO usersRTO = new UsersRTO(new UserView().setViewFromEntity(user, true), getRESTAPIBaseURI());
		usersRTO.setAppEnvName(getAppConfigService().getAppEnvName());
		usersRTO.buildStoriesLinks(getStoryService().findStoryLinkDTOsWithUserUID(userUID));

		return Response.status(Status.OK).entity(usersRTO).build();
	}

	@Path("/oauth2uid/{oauth2uid}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findUserOAuth2WithOAuth2UID(@PathParam("oauth2uid") String oAuth2UID,
			@Context HttpHeaders headers) {

		logger.entry(oAuth2UID);

		if (!isRESTRequestAuthorized(headers)) {
			return Response.status(Status.FORBIDDEN).build();
		}

		UserOAuth2 userOAuth2 = null;
		try {
			userOAuth2 = getUserOAuth2Service().findUserOAuthWithOAuthUID(oAuth2UID);
		} catch (AppCodeException e) {
			logger.warn(e.getI18NMessage());

			return Response.status(Status.NOT_FOUND).entity(e.getI18NMessage()).build();
		}

		UsersRTO usersRTO = new UsersRTO(new UserOAuth2View().setViewFromEntity(userOAuth2, true), getRESTAPIBaseURI());
		usersRTO.setAppEnvName(getAppConfigService().getAppEnvName());
		usersRTO.buildStoriesLinks(getStoryService().findStoryLinkDTOsWithUserOAuthUID(oAuth2UID));

		return Response.status(Status.OK).entity(usersRTO).build();
	}
}
