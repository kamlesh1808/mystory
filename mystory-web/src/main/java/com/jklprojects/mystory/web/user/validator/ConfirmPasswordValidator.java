/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.validator;

import com.jklprojects.mystory.common.i18n.I18N;
import com.jklprojects.mystory.web.api.validator.AbstractFacesValidator;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Validate two fields values for password and confirm password are equal.
 *
 * @author Kamleshkumar Patel
 * @version 1, 2015-MAR-31
 */
@FacesValidator(value = "confirmPasswordValidator")
public class ConfirmPasswordValidator extends AbstractFacesValidator {
	private static final XLogger logger = XLoggerFactory.getXLogger(ConfirmPasswordValidator.class);

	@Override
	public void validate(FacesContext fc, UIComponent uiComp, Object value) throws ValidatorException {

		String confirmPassword = (String) uiComp.getAttributes().get("confirmPasswordAttr");

		if (value == null || confirmPassword == null) {
			logger.trace("both password are null");
			return;
		}

		if (!value.equals(confirmPassword)) {
			String defaultErrorMsg = "Passwords do not match";
			String message = I18N.APP.getI18N("useraccount_passwordsNotMatch");

			logger.trace(defaultErrorMsg);
			throwValidatorException(!message.isEmpty() ? message : defaultErrorMsg, FacesMessage.SEVERITY_ERROR);
		}
	}
}
