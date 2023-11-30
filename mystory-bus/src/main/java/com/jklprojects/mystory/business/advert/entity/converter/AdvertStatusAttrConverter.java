/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity.converter;

import com.jklprojects.mystory.common.advert.AdvertStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-DEC-31
 */
@Converter(autoApply = true)
public class AdvertStatusAttrConverter implements AttributeConverter<AdvertStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AdvertStatus attribute) {
		return attribute.getId();
	}

	@Override
	public AdvertStatus convertToEntityAttribute(Integer dbData) {
		return AdvertStatus.toEnum(dbData);
	}
}
