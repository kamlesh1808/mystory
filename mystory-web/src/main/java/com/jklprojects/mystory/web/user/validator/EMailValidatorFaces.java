/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.validator;

import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.common.user.validator.EMailValidator;
import com.jklprojects.mystory.web.api.validator.AbstractFacesValidator;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-MAR-31
 */
@FacesValidator(value = "eMailValidator")
public class EMailValidatorFaces extends AbstractFacesValidator {
	private static final XLogger logger = XLoggerFactory.getXLogger(EMailValidatorFaces.class);

	@Override
	public void validate(FacesContext fc, UIComponent uiComp, Object value) throws ValidatorException {
		if (!EMailValidator.getInstance().isValid((String) value)) {

			String defaultErrorMsg = "Invalid E-Mail format.";
			String message = I18N.APP.getI18N("common_emailInvalid");

			logger.trace(defaultErrorMsg);
			throwValidatorException(!message.isEmpty() ? message : defaultErrorMsg, FacesMessage.SEVERITY_ERROR);
		}
	}
}
