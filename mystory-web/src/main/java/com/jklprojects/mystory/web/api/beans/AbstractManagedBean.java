/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.api.beans;

import com.jklprojects.mystory.business.api.entity.IPersistence;
import com.jklprojects.mystory.business.api.entity.view.AbstractIDView;
import com.jklprojects.mystory.business.common.util.INetUtil;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.ex.AppExceptionSeverity;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.web.AppURLRewriteConsts;
import com.jklprojects.mystory.web.JSFNavigation;
import com.jklprojects.mystory.web.RequestParamNames;
import com.jklprojects.mystory.web.SessionAttributes;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLogger.Level;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents common/reusable Managed Bean functionalities.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @version 1, 2016-04-15
 */
public abstract class AbstractManagedBean
		implements
			Serializable,
			IPersistence,
			SessionAttributes,
			AppURLRewriteConsts,
			RequestParamNames {

	private static final long serialVersionUID = 1945198129427243716L;

	private static final String MSG_INFO = "msgInfo";
	private static final String MSG_WARN = "msgWarn";
	private static final String MSG_ERROR = "msgError";

	private static final XLogger logger = XLoggerFactory.getXLogger(AbstractManagedBean.class);

	public AbstractManagedBean() {
		super();
	}

	/**
	 * Operation cancelled.
	 *
	 * @return {@link JSFNavigation#CANCEL}
	 */
	public String doCancel() {
		return JSFNavigation.CANCEL.getNavigation();
	}

	public ServletContext getAppServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

	public String getClientIPAddress() {
		return INetUtil.getClientIpAddress(getHttpServletRequest());
	}

	public HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public String getRequestURI() {
		return ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getRequestURI();
	}

	public String getViewId() {
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	public boolean isValidUpdate(AbstractIDView<?> view) {
		return view != null && view.getId() > 0;
	}

	/**
	 * Retrieve a {@link List} of user messages from current session and add them to
	 * {@link FacesContext} for display on the growlMessagesComp UI.
	 *
	 * @return empty String
	 */
	public String prepareUserMessagesList() {
		String key = USER_MESSAGES_LIST;

		@SuppressWarnings("unchecked")
		List<String> userMessages = (List<String>) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(key);

		if (CollUtil.isNotEmpty(userMessages)) {
			for (String msg : userMessages) {
				addMsg(msg, null, FacesMessage.SEVERITY_INFO, false);
			}
		}

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);

		return "";
	}

	public String redirectToCurrentPage() {
		String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		return viewId + "?faces-redirect=true";
	}

	private FacesMessage.Severity getFacesMessageSeverity(AppException e) {
		if (e.getAppExceptionSeverity() == AppExceptionSeverity.ERROR) {
			return FacesMessage.SEVERITY_ERROR;
		} else if (e.getAppExceptionSeverity() == AppExceptionSeverity.WARN) {
			return FacesMessage.SEVERITY_WARN;
		} else if (e.getAppExceptionSeverity() == AppExceptionSeverity.INFO) {
			return FacesMessage.SEVERITY_INFO;
		} else if (e.getAppExceptionSeverity() == AppExceptionSeverity.FATAL) {
			return FacesMessage.SEVERITY_FATAL;
		}
		return FacesMessage.SEVERITY_ERROR;
	}

	/**
	 * Add message. Keep messages in {@code Flash} by default.
	 *
	 * @param msgSummary
	 * @param msgDetail
	 * @param severity
	 */
	protected void addMsg(String msgSummary, String msgDetail, Severity severity) {
		addMsg(msgSummary, msgDetail, severity, true);
	}

	/**
	 * Add message.
	 *
	 * @param msgSummary
	 * @param msgDetail
	 * @param severity
	 * @param keepMsgOnRedirect
	 *            in {@code Flash} if true
	 */
	protected void addMsg(String msgSummary, String msgDetail, Severity severity, boolean keepMsgOnRedirect) {
		logger.entry(msgSummary, msgDetail);

		FacesContext facesCtxt = FacesContext.getCurrentInstance();

		String forMsg = MSG_INFO;
		if (FacesMessage.SEVERITY_INFO == severity) {
			forMsg = MSG_INFO;
		} else if (FacesMessage.SEVERITY_WARN == severity) {
			forMsg = MSG_WARN;
		} else if (FacesMessage.SEVERITY_ERROR == severity || FacesMessage.SEVERITY_FATAL == severity) {
			forMsg = MSG_ERROR;
		}

		facesCtxt.addMessage(forMsg, new FacesMessage(severity, msgSummary, msgDetail));
		facesCtxt.getExternalContext().getFlash().setKeepMessages(keepMsgOnRedirect);
	}

	protected HttpSession getSession(boolean create) {
		HttpServletRequest httpReq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return httpReq.getSession(create);
	}

	protected Object getSessionAttribute(String attrName) {
		HttpSession session = getSessionFromExternalContext(false);
		return session != null ? session.getAttribute(attrName) : null;
	}

	protected HttpSession getSessionFromExternalContext(boolean create) {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(create);
	}

	protected <E extends AppCodeException> void processAppCodeException(E e) {
		String msg = e.getI18NMessage();
		logger.warn(msg);

		addMsg(msg, "", getFacesMessageSeverity(e));
	}

	protected <E extends AppException> void processAppException(E e) {
		if (e instanceof AppCodeException) {
			processAppCodeException((AppCodeException) e);
		} else {
			logger.catching(Level.ERROR, e);
			addMsg(e.getMessage(), "", getFacesMessageSeverity(e));
		}
	}

	protected HttpSession setSessionAttribute(String attrName, Object attrValue, boolean create) {
		HttpSession session = getSession(create);
		if (session != null) {
			session.setAttribute(attrName, attrValue);
		}
		return session;
	}
}
