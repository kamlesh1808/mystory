/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Oct 6, 2015
 */
@Converter
public class AuditEventTypeAttrConverter implements AttributeConverter<AuditEventType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AuditEventType attribute) {
		return attribute.getId();
	}

	@Override
	public AuditEventType convertToEntityAttribute(Integer dbData) {
		return AuditEventType.toEnum(dbData);
	}
}
