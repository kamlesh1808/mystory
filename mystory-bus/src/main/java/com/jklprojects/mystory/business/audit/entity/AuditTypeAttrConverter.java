/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.entity;

import com.jklprojects.mystory.common.audit.AuditType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-05-23
 * @since 2.18.05.23
 */
@Converter
public class AuditTypeAttrConverter implements AttributeConverter<AuditType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AuditType attribute) {
		return attribute.getId();
	}

	@Override
	public AuditType convertToEntityAttribute(Integer dbData) {
		return AuditType.toEnum(dbData);
	}
}
