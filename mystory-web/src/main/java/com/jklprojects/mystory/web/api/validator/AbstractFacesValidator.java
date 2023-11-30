/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.api.validator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLogger.Level;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Apr 26, 2015 11:05:03 AM
 */
public abstract class AbstractFacesValidator implements Validator {
	private static final XLogger logger = XLoggerFactory.getXLogger(AbstractFacesValidator.class);

	/**
	 * @param message
	 * @param severity
	 * @throws ValidatorException
	 */
	protected void throwValidatorException(String message, Severity severity) throws ValidatorException {

		FacesMessage facesMsg = new FacesMessage(message);
		facesMsg.setSeverity(severity);

		ValidatorException t = new ValidatorException(facesMsg);
		logger.throwing(Level.DEBUG, t);

		throw t;
	}
}
