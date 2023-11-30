/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.service.rs;

import com.jklprojects.mystory.business.api.rs.AbstractRESTService;
import com.jklprojects.mystory.business.story.entity.view.TagView;
import com.jklprojects.mystory.business.story.service.api.TagService;
import com.jklprojects.mystory.business.story.service.rs.rto.TagsRTO;
import com.jklprojects.mystory.common.story.TagStatus;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-04-23
 * @version 2, 2017-12-23
 * @version 2, 2018-04-23
 */
@Stateless
@Path("/" + TagsRTO.RELATIONSHIP_TYPE)
public class TagsRS extends AbstractRESTService {
	private static final XLogger logger = XLoggerFactory.getXLogger(TagsRS.class);

	@EJB
	private TagService tagService;

	@Path("/{tagName}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response findTagWithName(@PathParam("tagName") String tagName, @Context HttpHeaders headers) {
		logger.entry(tagName);

		TagView tagView = tagService.findTagWithName(tagName);

		TagsRTO tagsRTO = new TagsRTO(tagView);
		tagsRTO.setAppEnvName(getAppConfigService().getAppEnvName());

		return Response.status(Status.OK).entity(tagsRTO).build();
	}

	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createTag(@FormParam("tagName") String name, @FormParam("desc") String desc,
			@FormParam("status") TagStatus status, @Context HttpHeaders headers, @Context UriInfo uriInfo) {

		logger.info(name, desc, status);

		if (!isRESTRequestAuthorized(headers)) {
			return Response.status(Status.FORBIDDEN).build();
		}

		TagView newTag = new TagView();
		newTag.setName(name);
		newTag.setDesc(desc);
		newTag.setStatus(status);

		TagView tagView = tagService.createTag(newTag);

		TagsRTO tagsRTO = new TagsRTO(tagView);
		tagsRTO.setAppEnvName(getAppConfigService().getAppEnvName());

		return Response.created(uriInfo.getAbsolutePath()).entity(tagsRTO).build();
	}
}
