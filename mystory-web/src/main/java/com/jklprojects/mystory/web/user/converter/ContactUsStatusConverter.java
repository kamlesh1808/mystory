/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.converter;

import com.jklprojects.mystory.common.user.ContactUsStatus;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Aug 1, 2015
 */
@FacesConverter("contactUsStatusConverter")
public class ContactUsStatusConverter extends EnumConverter {
	public ContactUsStatusConverter() {
		super(ContactUsStatus.class);
	}
}
