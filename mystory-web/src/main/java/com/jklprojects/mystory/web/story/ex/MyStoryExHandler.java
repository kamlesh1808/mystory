/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.ex;

import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Jul 28, 2015
 */
public class MyStoryExHandler extends ExceptionHandlerWrapper {
	private final ExceptionHandler wrapped;

	public MyStoryExHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		for (Iterator<?> itr = getUnhandledExceptionQueuedEvents().iterator(); itr.hasNext();) {
			ExceptionQueuedEvent event = (ExceptionQueuedEvent) itr.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable t = context.getException();
			FacesContext fc = FacesContext.getCurrentInstance();

			Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
			NavigationHandler nav = fc.getApplication().getNavigationHandler();
			try {
				if (t instanceof ViewExpiredException) {
					ViewExpiredException ex = (ViewExpiredException) t;

					requestMap.put("currentViewId", ex.getViewId());
					nav.handleNavigation(fc, null, "viewExpired");
					fc.renderResponse();
				}

			} finally {
				// remove so to not be handled by parent exception handler
				itr.remove();
			}
		}

		// parent should handle the rest
		getWrapped().handle();
	}
}
