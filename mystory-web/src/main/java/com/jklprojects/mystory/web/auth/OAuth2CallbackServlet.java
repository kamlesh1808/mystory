/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.jklprojects.mystory.business.api.auth.GUserInfo;
import com.jklprojects.mystory.business.audit.api.AuditService;
import com.jklprojects.mystory.business.audit.entity.Audit;
import com.jklprojects.mystory.business.audit.entity.AuditDetail;
import com.jklprojects.mystory.business.audit.entity.AuditEventType;
import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.business.user.entity.UserOAuth2;
import com.jklprojects.mystory.business.user.service.api.UserOAuth2Service;
import com.jklprojects.mystory.common.audit.AuditAttrType;
import com.jklprojects.mystory.common.audit.AuditType;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppRuntimeException;
import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.oauth2.IOAuth2Consts;
import com.jklprojects.mystory.common.user.UserExceptions;
import com.jklprojects.mystory.common.user.UserRoleType;
import com.jklprojects.mystory.common.user.dto.SignedInUserBean;
import com.jklprojects.mystory.common.user.validator.EMailValidator;
import com.jklprojects.mystory.common.util.AppUtil;
import com.jklprojects.mystory.web.AppURLRewriteConsts;
import com.jklprojects.mystory.web.SessionAttributes;
import com.jklprojects.mystory.web.api.servlet.AppServletUtil;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * This OAuth2 Callback Servlet is called by the OAuth2 provider such as Google.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.03.23
 */
@WebServlet(urlPatterns = "/oauth2callback", asyncSupported = true)
public class OAuth2CallbackServlet extends HttpServlet implements SessionAttributes, AppURLRewriteConsts {

	private static final long serialVersionUID = -1614201536873336209L;

	private static final XLogger logger = XLoggerFactory.getXLogger(OAuth2CallbackServlet.class);

	private final ObjectMapper jsonMapper = new ObjectMapper();

	@EJB
	private UserOAuth2Service userOAuth2Service;

	@EJB
	private AppConfigService appConfigService;

	@EJB
	private AuditService auditService;

	/** @see HttpServlet#HttpServlet() */
	public OAuth2CallbackServlet() {
		super();
	}

	/**
	 * @param req
	 * @param res
	 * @throws AppRuntimeException
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		UserRoleType userRoleType = (UserRoleType) AppServletUtil.getSessionAttribute(req,
				SessionAttributes.USER_ROLE_TYPE);

		if (userRoleType != null && userRoleType.isGoogle()) {
			try {
				signInWithGoogle(req, res);
			} catch (AppCodeException e) {
				logger.warn(e.getI18NMessage(), e);
			}
		}
	}

	/**
	 * @param req
	 * @param res
	 * @throws AppRuntimeException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	/**
	 * https://console.cloud.google.com/apis/dashboard
	 *
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 * @throws AppCodeException
	 */
	private void signInWithGoogle(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException, AppCodeException {

		OAuth20Service oAuth20Service = (OAuth20Service) AppServletUtil.getSessionAttribute(req,
				SessionAttributes.OAUTH2_SERVICE);

		String authorizationURL = AppServletUtil.getFullURL(req);

		logger.info("userRoleTypeName {}, authorizationURL {} ", UserRoleType.GOOGLE.getUserRoleTypeName(),
				authorizationURL);

		try {
			String code = AppServletUtil.getParameterValue(authorizationURL, IOAuth2Consts.GOOGLE_PARAM_NAME_CODE);

			// Trade the Request Token and Verifier for the Access Token
			OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
			logger.info("getAccessToken {} ", accessToken);

			OAuthRequest request = new OAuthRequest(Verb.GET, IOAuth2Consts.GOOGLE_USERINFO_V2_ME_URL);
			oAuth20Service.signRequest(accessToken, request);
			Response response = oAuth20Service.execute(request);

			String googleUserInfoJSON = response.getBody();
			logger.info("googleUserInfoJSON {}", googleUserInfoJSON);
			logger.info("response.getCode() {}", response.getCode());

			GUserInfo gUserInfo = jsonMapper.readValue(googleUserInfoJSON, GUserInfo.class);
			logger.info("gUserInfo {}", gUserInfo.toString());
			validateEmail(gUserInfo.getEmail());

			String userName = AppUtil.createtUserNameFromEmail(gUserInfo.getEmail(), UserRoleType.GOOGLE);
			gUserInfo.setUserName(userName);

			UserOAuth2 userOAuth2 = userOAuth2Service.findUserOAuthWithOAuthUIDUserRoleType(gUserInfo.getId(),
					UserRoleType.GOOGLE);

			if (userOAuth2 == null) { // user was not found, so create it
				userOAuth2 = userOAuth2Service.createUserOAuth2(gUserInfo);
			}

			if (userOAuth2 != null) {
				startOAuth2Session(req, res, userOAuth2);
			}
		} catch (AppCodeException e) {
			throw new AppRuntimeException(e.getMessage(), e);
		} catch (URISyntaxException | InterruptedException | ExecutionException | PersistenceException e) {
			throw new AppRuntimeException(e.getMessage(), e);
		}
	}

	private void validateEmail(String email) throws AppCodeException {
		if (!EMailValidator.getInstance().isValid(email)) {
			AppCodeException t = UserExceptions.USER_ACCOUNT_009.newAppCodeException(email);
			throw t;
		}
	}

	private void startOAuth2Session(HttpServletRequest req, HttpServletResponse res, UserOAuth2 userOAuth2)
			throws ServletException, IOException {

		HttpSession session = req.getSession(true);

		SignedInUserBean signedInUserBean = new SignedInUserBean(userOAuth2.getId(), userOAuth2.getUserName(),
				userOAuth2.getUserRoleType(), String.valueOf(userOAuth2.getIdOAuth2()), userOAuth2.getFirstName(),
				userOAuth2.getLastName());
		AppServletUtil.setSessionAttribute(req, SIGNED_IN_USER_BEAN, signedInUserBean);

		Audit audit = new Audit(userOAuth2, AuditType.USER_OAUTH2_AUTHENTICATION, AuditEventType.CREATE,
				userOAuth2.getIdOAuth2());
		audit.addAuditDetail(
				new AuditDetail(AuditAttrType.USER_OAUTH2_SESSION_SUCCESS.getAttrName(), Boolean.TRUE.toString()));

		auditService.createAudit(audit);

		List<String> userMessagesList = Arrays.asList(
				I18N.APP.getI18N("signin_signinSuccessMsg", userOAuth2.getUserName(), appConfigService.getAppName()));
		AppServletUtil.setSessionAttribute(session, USER_MESSAGES_LIST, userMessagesList);

		logger.info("New UserOAuth2 signin User: userName {}, OAuth2UID {}, userRoleType {}, session {} ",
				userOAuth2.getUserName(), userOAuth2.getIdOAuth2(), userOAuth2.getUserRoleType(), session.getId());

		res.sendRedirect(new StringBuilder(USER).toString());
	}
}
