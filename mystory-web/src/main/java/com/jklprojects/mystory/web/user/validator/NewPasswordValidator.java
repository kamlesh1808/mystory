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
 * Validate current password is not the same as new password.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-APR-17
 */
@FacesValidator(value = "newPasswordValidator")
public class NewPasswordValidator extends AbstractFacesValidator {
	private static final XLogger logger = XLoggerFactory.getXLogger(NewPasswordValidator.class);

	@Override
	public void validate(FacesContext fc, UIComponent uiComp, Object value) throws ValidatorException {

		String newPassword = (String) uiComp.getAttributes().get("newPasswordAttr");

		if (value == null || newPassword == null) {
			logger.trace("value {} or newPassword {} is null", value, newPassword);
			return;
		}

		if (value.equals(newPassword)) {
			String defaultErrorMsg = "New password must be different from current password";
			String message = I18N.APP.getI18N("useraccount_newPasswordsNotDifferent");

			logger.trace(defaultErrorMsg);
			throwValidatorException(!message.isEmpty() ? message : defaultErrorMsg, FacesMessage.SEVERITY_ERROR);
		}
	}
}
