/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.story.entity.converter;

import com.jklprojects.mystory.common.story.TagStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 16, 2015
 */
@Converter
public class TagStatusAttrConverter implements AttributeConverter<TagStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(TagStatus attribute) {
		return attribute.getId();
	}

	@Override
	public TagStatus convertToEntityAttribute(Integer dbData) {
		return TagStatus.toEnum(dbData);
	}
}
