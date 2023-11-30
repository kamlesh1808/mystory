/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.api.servlet;

import static com.jklprojects.mystory.web.AppURLRewriteConsts.JAVAX_FACES_RESOURCE_URI;
import static com.jklprojects.mystory.web.AppURLRewriteConsts.SECURED_RESOURCE_URI_APP;

import com.jklprojects.mystory.business.common.util.INetUtil;
import com.jklprojects.mystory.common.user.dto.SignedInUserBean;
import com.jklprojects.mystory.web.SessionAttributes;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 2, 2017-12-23
 * @version 2, 2018-03-23
 * @version 2, 2018-04-23
 */
public class AppServletUtil {
	private static final XLogger logger = XLoggerFactory.getXLogger(AppServletUtil.class);

	public AppServletUtil() {
	}

	public static String getClientIPAddress(HttpServletRequest req) {
		return INetUtil.getClientIpAddress(req);
	}

	/**
	 * Return full URL including the query string, if it was present.
	 *
	 * @param request
	 * @return
	 */
	public static String getFullURL(HttpServletRequest request) {
		StringBuffer reqURL = request.getRequestURL();
		String queryStr = request.getQueryString();

		return queryStr != null ? reqURL.append('?').append(queryStr).toString() : reqURL.toString();
	}

	/**
	 * Get value of the parameter name from the uri.
	 *
	 * @param uri
	 * @param paramName
	 * @return
	 * @throws URISyntaxException
	 */
	public static String getParameterValue(String uri, String paramName) throws URISyntaxException {
		String value = "";
		List<NameValuePair> urlParams = URLEncodedUtils.parse(new URI(uri), StandardCharsets.UTF_8);

		if (urlParams != null) {
			for (NameValuePair nvp : urlParams) {
				if (nvp.getName().equalsIgnoreCase(paramName)) {
					value = nvp.getValue();
					break;
				}
			}
		}
		return value;
	}

	public static Object getSessionAttribute(HttpServletRequest req, String attrName) {
		HttpSession session = req.getSession(false);
		return session != null ? session.getAttribute(attrName) : null;
	}

	/** @return signed in userName or null if it does not exist */
	public static String getSignedInUserName(HttpServletRequest req) {
		SignedInUserBean signedInUserBean = (SignedInUserBean) getSessionAttribute(req,
				SessionAttributes.SIGNED_IN_USER_BEAN);

		return signedInUserBean != null ? signedInUserBean.getUserName() : "";
	}

	/** @return signed in user UID or null if not found */
	public static String getSignedInUserUID(HttpServletRequest req) {
		SignedInUserBean signedInUserBean = (SignedInUserBean) getSessionAttribute(req,
				SessionAttributes.SIGNED_IN_USER_BEAN);

		return signedInUserBean != null ? signedInUserBean.getUserUID() : "";
	}

	/**
	 * @param httpSession
	 * @return true if user session is active, false otherwise.
	 */
	public static boolean isSessionActive(HttpSession httpSession) {
		return httpSession != null && httpSession.getAttribute(SessionAttributes.SIGNED_IN_USER_BEAN) != null;
	}

	public static boolean isURIJavaxFacesResources(HttpServletRequest req) {
		return req.getRequestURI().indexOf(JAVAX_FACES_RESOURCE_URI) >= 0;
	}

	public static boolean isURISecure(HttpServletRequest req) {
		return req.getRequestURI().indexOf(SECURED_RESOURCE_URI_APP) >= 0;
	}

	public static void logRequestURI(HttpServletRequest req, String reqURI) {
		if (!isURIJavaxFacesResources(req)) {
			logger.trace("Request URI: {}, clientIPAddress: {}, getSignedInUserUID: {}", reqURI,
					INetUtil.getClientIpAddress(req), getSignedInUserUID(req));
		}
	}

	public static void setSessionAttribute(HttpServletRequest req, String attrName, Object attrValue) {
		HttpSession session = req.getSession(false);

		if (session != null) {
			session.setAttribute(attrName, attrValue);
		}
	}

	public static void setSessionAttribute(HttpSession session, String attrName, Object attrValue) {
		if (session != null) {
			session.setAttribute(attrName, attrValue);
		}
	}
}
