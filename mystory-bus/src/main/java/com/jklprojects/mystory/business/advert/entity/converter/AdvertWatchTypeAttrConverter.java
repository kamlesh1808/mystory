/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity.converter;

import com.jklprojects.mystory.common.advert.AdvertWatchType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 0.31.0-2017-Jan-23
 */
@Converter(autoApply = true)
public class AdvertWatchTypeAttrConverter implements AttributeConverter<AdvertWatchType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AdvertWatchType attribute) {
		return attribute.getId();
	}

	@Override
	public AdvertWatchType convertToEntityAttribute(Integer dbData) {
		return AdvertWatchType.toEnum(dbData);
	}
}
