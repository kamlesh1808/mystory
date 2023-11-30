/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.api.event;

import javax.faces.component.UIInput;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

/**
 * Highlight the "invalid" component with CSS.
 *
 * @see faces-config.xml for system-event-listener configuration
 * @author Kamleshkumar N. Patel
 * @version 1, Apr 22, 2015
 */
public class PostValidationListener implements SystemEventListener {

	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		UIInput source = (UIInput) event.getSource();

		if (!source.isValid()) {
			source.getAttributes().put("styleClass", "input-invalid");
		} else {
			source.getAttributes().put("styleClass", "");
		}
	}

	@Override
	public boolean isListenerForSource(Object source) {
		return true;
	}
}
