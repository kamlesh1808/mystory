/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.entity.converter;

import com.jklprojects.mystory.common.AppPage;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-06-23
 * @since 2.18.06.23
 */
@Converter
public class AppPageAttrConverter implements AttributeConverter<AppPage, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AppPage attribute) {
		return attribute.getId();
	}

	@Override
	public AppPage convertToEntityAttribute(Integer dbData) {
		return AppPage.toEnum(dbData);
	}
}
