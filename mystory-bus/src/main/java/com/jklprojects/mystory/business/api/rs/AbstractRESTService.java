/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.rs;

import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.business.common.util.INetUtil;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2, 2017-12-23
 */
public abstract class AbstractRESTService {
	private static final XLogger logger = XLoggerFactory.getXLogger(AbstractRESTService.class);

	@Inject
	private AppConfigService appConfigService;

	@Context
	private HttpServletRequest httpServletRequest;

	public AbstractRESTService() {
		super();
	}

	@Path("appconfig/{propertyname}")
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public String getAppConfig(@PathParam("propertyname") String propertyName, @Context HttpHeaders headers) {
		return getAppConfigService().getProperty(propertyName);
	}

	public AppConfigService getAppConfigService() {
		return appConfigService;
	}

	public final String getClientIPAddress() {
		return INetUtil.getClientIpAddress(httpServletRequest);
	}

	public final String getRequestPath() {
		return httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
	}

	public final String getRESTAPIBaseURI() {
		return getAppConfigService().getRESTAPIBaseURI();
	}

	/**
	 * Determine if the REST request is authorized. Returns true if a HTTP request
	 * header exists and has a specific value.
	 *
	 * @param headers
	 * @return
	 */
	public final boolean isRESTRequestAuthorized(HttpHeaders headers) {
		if (isHeaderValueEqual(headers, getAppConfigService().getRESTAPIAuthorizedHeaderName(),
				getAppConfigService().getRESTAPIAuthorizedKey())) {
			return true;
		} else {
			logger.warn("REST API FORBIDDEN request, path: {} clientIPAddress: {}", getRequestPath(),
					getClientIPAddress());
			return false;
		}
	}

	@Path("up")
	@GET
	public String up() {
		return "up";
	}

	@Path("up/{pathparam}")
	@GET
	public String upPathParam(@PathParam("pathparam") String pathParam, @Context HttpHeaders headers) {
		return new StringBuilder("up").append(" ").append(pathParam).toString();
	}

	private final boolean isHeaderValueEqual(HttpHeaders headers, String headerName, String headerValue) {
		return headerValue.equals(headers.getHeaderString(headerName));
	}
}
