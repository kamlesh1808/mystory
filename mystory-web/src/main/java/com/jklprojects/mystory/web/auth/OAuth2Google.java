/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.auth;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.business.common.util.EncryptDecryptor;
import com.jklprojects.mystory.common.ex.AppRuntimeException;
import com.jklprojects.mystory.common.oauth2.IOAuth2Consts;
import com.jklprojects.mystory.common.oauth2.OAuth2PropertiesBean;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.web.SessionAttributes;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Responsible for handling click on the Google Sign in button. The handling
 * actions include initializing Google API Service, getting authorization URL
 * and redirecting to the {@link OAuth2CallbackServlet}.
 *
 * <pre>
* https://developers.google.com/identity/protocols/googlescopes
 * </pre>
 *
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-03-23
 * @since 2.18.03.23
 * @see {@link OAuth20Service}
 * @see ({@link GoogleApi20}
 */
@WebServlet("/OAuth2Google")
public class OAuth2Google extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private AppConfigService appConfigService;

	private final EncryptDecryptor encryptDecryptor;

	private OAuth20Service oAuth20Service;

	private static final XLogger logger = XLoggerFactory.getXLogger(OAuth2Google.class);

	/** @see HttpServlet#HttpServlet() */
	public OAuth2Google() {
		super();

		encryptDecryptor = EncryptDecryptor.getInstance();
	}

	@PostConstruct
	private void initGoogleAPI20Service() {
		OAuth2PropertiesBean oAuth2PropertiesBean = appConfigService.getOAuth2PropertiesBean();

		if (oAuth2PropertiesBean != null) {
			// decrypt clientId and clientSecret
			String clientId = encryptDecryptor.decrypt(oAuth2PropertiesBean.getClientId());
			String clientSecret = encryptDecryptor.decrypt(oAuth2PropertiesBean.getClientSecret());
			String callbackURI = oAuth2PropertiesBean.getCallbackURI();

			// build oAuth2 service for Google
			oAuth20Service = new ServiceBuilder(clientId).apiSecret(clientSecret).callback(callbackURI)
					.defaultScope(IOAuth2Consts.GOOGLE_SCOPE_PROFILE + " " + IOAuth2Consts.GOOGLE_SCOPE_EMAIL)
					.build(GoogleApi20.instance());
		} else {
			throw new AppRuntimeException("OAuth2PropertiesBean was null");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession sess = req.getSession();
		sess.setAttribute(SessionAttributes.OAUTH2_SERVICE, oAuth20Service);
		sess.setAttribute(SessionAttributes.USER_ROLE_TYPE, UserRoleType.GOOGLE);

		String authorizationURL = oAuth20Service.getAuthorizationUrl();
		logger.info("Redirecting to authorizationURL {} ", authorizationURL);
		res.sendRedirect(authorizationURL);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
