/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.entity.view;

import com.jklprojects.mystory.business.common.entity.table.CountryView;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-08-09
 */
@DisplayName("Country View Test")
class CountryViewTest {

	@Test
	@DisplayName("Test Country View Sort")
	void testCountryViewSort() {
		List<CountryView> listCountry = new ArrayList<>();
		CountryView canada = new CountryView();
		canada.setNameEn("Canada");
		listCountry.add(canada);
		CountryView cambodia = new CountryView();
		cambodia.setNameEn("Cambodia");
		listCountry.add(cambodia);
		List<CountryView> sorted = listCountry.parallelStream().sorted().collect(Collectors.toList());
		for (int i = 0; i < sorted.size(); i++) {
			if (i == 0) {
				Assertions.assertTrue(sorted.get(0).getNameEn().equals(cambodia.getNameEn()));
			}
			if (i == 1) {
				Assertions.assertTrue(sorted.get(1).getNameEn().equals(canada.getNameEn()));
			}
		}
	}
}
