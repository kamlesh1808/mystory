/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.converter;

import com.jklprojects.mystory.common.user.ContactUsStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, July 29, 2015
 */
@Converter
public class ContactUsStatusAttrConverter implements AttributeConverter<ContactUsStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ContactUsStatus attribute) {
		return attribute.getId();
	}

	@Override
	public ContactUsStatus convertToEntityAttribute(Integer dbData) {
		return ContactUsStatus.toEnum(dbData);
	}
}
