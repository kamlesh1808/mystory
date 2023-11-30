/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.service.rs;

import com.jklprojects.mystory.business.api.rs.AbstractRESTService;
import com.jklprojects.mystory.business.story.entity.view.StoryView;
import com.jklprojects.mystory.business.story.service.api.StoryService;
import com.jklprojects.mystory.business.story.service.rs.rto.StoriesRTO;
import com.jklprojects.mystory.common.ex.AppCodeException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
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
 * @version 1, 2017-04-23
 * @version 2, 2017-12-23
 * @version 2, 2018-04-23
 * @version 3, 2018-11-23
 * @version 4, 2019-03-23
 */
@Stateless
@Path(StoriesRTO.RELATIONSHIP_TYPE)
public class StoriesRS extends AbstractRESTService {
	private static final XLogger logger = XLoggerFactory.getXLogger(StoriesRS.class);

	@EJB
	private StoryService storyService;

	@Path("{storyId}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findStoryWithStoryId(@PathParam("storyId") long storyId, @Context HttpHeaders headers) {
		if (!isRESTRequestAuthorized(headers)) {
			Response.status(Status.FORBIDDEN).build();
		}

		StoryView storyView = null;
		try {
			storyView = storyService.findStory(storyId);
		} catch (AppCodeException e) {
			logger.catching(e);

			throw new NotFoundException(e.getI18NMessage(), e);
		}

		StoriesRTO storiesRTO = new StoriesRTO(storyView, getRESTAPIBaseURI());
		storiesRTO.setAppEnvName(getAppConfigService().getAppEnvName());

		return Response.status(Status.OK).entity(storiesRTO).build();
	}
}
