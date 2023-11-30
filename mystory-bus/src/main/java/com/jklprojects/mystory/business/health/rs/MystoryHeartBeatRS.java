/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.health.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2018-06-23
 */
@Path("/heartbeat/mystory")
public class MystoryHeartBeatRS {

	@GET
	@Path("/alive")
	public Response alive() {
		return Response.ok().entity("REST - I am alive!").build();
	}

	@GET
	@Path("/database")
	public Response database() {
		return Response.ok().entity("Database - I am alive!").build();
	}
}
