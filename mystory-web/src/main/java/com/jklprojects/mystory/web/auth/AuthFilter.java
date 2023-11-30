/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.auth;

import com.jklprojects.mystory.web.AppURLRewriteConsts;
import com.jklprojects.mystory.web.api.servlet.AppServletUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Responsible for managing user authentication flow. It protects "secured"
 * resources by redirecting to sign-in page if an user session does not exist.
 *
 * <p>
 * The filter is configured in the web.xml. The list order is the order in which
 * filters are executed.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-11
 * @version 2, 2017-12-23
 * @version 2, 2018-03-23
 */
public class AuthFilter implements Filter, AppURLRewriteConsts {
	private static final XLogger logger = XLoggerFactory.getXLogger(AuthFilter.class);

	public AuthFilter() {
		super();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			final HttpServletRequest req = (HttpServletRequest) request;
			final HttpServletResponse resp = (HttpServletResponse) response;
			final HttpSession httpSession = req.getSession(false);

			final String reqURI = req.getRequestURI();
			if (!AppServletUtil.isSessionActive(httpSession) && AppServletUtil.isURISecure(req)) {
				// protect "secured" resources by redirecting to signin page as
				// an active user session does not exist.
				resp.sendRedirect(req.getContextPath() + SIGN_IN);
				AppServletUtil.logRequestURI(req, reqURI);
			} else {
				chain.doFilter(request, response);
				AppServletUtil.logRequestURI(req, reqURI);
			}
		} catch (final Exception e) {
			logger.catching(e);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
