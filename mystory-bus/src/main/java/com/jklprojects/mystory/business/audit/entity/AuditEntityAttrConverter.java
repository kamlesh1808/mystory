/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Oct 6, 2015
 */
@Converter
public class AuditEntityAttrConverter implements AttributeConverter<AuditEntity, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AuditEntity attribute) {
		return attribute.getId();
	}

	@Override
	public AuditEntity convertToEntityAttribute(Integer dbData) {
		return AuditEntity.toEnum(dbData);
	}
}
