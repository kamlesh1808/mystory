/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.story;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * JUnit5 jupiter test
 *
 * @author Kamleshkumar N. Patel
 * @version 3, 2018-11-23
 */
@DisplayName("Story Name Test")
class StoryNameTest {

	@Test
	@DisplayName("Test Collect Ids")
	void testCollectIds() {
		List<Integer> storyNameIds = Arrays.asList(StoryName.values()).stream().map(StoryName::getId)
				.collect(Collectors.toList());
		Assertions.assertTrue(storyNameIds != null && !storyNameIds.isEmpty());
		// storyNameIds.forEach(i -> System.out.println(i));
	}

	@Test
	@DisplayName("Test In")
	void testIn() {
		Assertions.assertTrue(StoryName.BE_HEALED.in(StoryName.BE_HEALED));
		Assertions.assertTrue(StoryName.BE_HEALED.in(StoryName.BE_HEALED, StoryName.BITS_AND_BYTES));
		Assertions.assertFalse(StoryName.BE_HEALED.in(StoryName.BITS_AND_BYTES));
	}

	@Test
	@DisplayName("Test To Enum")
	void testToEnum() {
		Assertions.assertEquals(StoryName.BE_HEALED, StoryName.toEnum(StoryName.BE_HEALED.getId()));
		Assertions.assertEquals(StoryName.BITS_AND_BYTES, StoryName.toEnum(StoryName.BITS_AND_BYTES.getId()));
		Assertions.assertNotEquals(StoryName.BITS_AND_BYTES, StoryName.toEnum(StoryName.BE_HEALED.getId()));
	}
}
