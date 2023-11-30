/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity.converter;

import com.jklprojects.mystory.common.advert.AdvertPriorityType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-12-31
 * @version 3, 2018-10-23
 */
@Converter(autoApply = true)
public class AdvertPriorityTypeAttrConverter implements AttributeConverter<AdvertPriorityType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AdvertPriorityType attribute) {
		return attribute.getPriority();
	}

	@Override
	public AdvertPriorityType convertToEntityAttribute(Integer dbData) {
		return AdvertPriorityType.toEnum(dbData);
	}
}
