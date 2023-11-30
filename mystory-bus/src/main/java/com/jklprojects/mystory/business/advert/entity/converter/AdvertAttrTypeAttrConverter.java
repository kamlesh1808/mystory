/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity.converter;

import com.jklprojects.mystory.common.advert.AdvertAttrType;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-JAN-01
 */
@Converter(autoApply = true)
public class AdvertAttrTypeAttrConverter implements AttributeConverter<AdvertAttrType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AdvertAttrType attribute) {
		return attribute.getId();
	}

	@Override
	public AdvertAttrType convertToEntityAttribute(Integer dbData) {
		return AdvertAttrType.toEnum(dbData);
	}
}
