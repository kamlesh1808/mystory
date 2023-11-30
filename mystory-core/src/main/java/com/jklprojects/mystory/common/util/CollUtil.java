/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Jul 6, 2015
 */
public class CollUtil {

	/**
	 * Return true if the given object's type is either Collection or Map.
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isCollectionOrMap(Object obj) {
		return obj instanceof Collection || obj instanceof Map;
	}

	/**
	 * Return true if collection is null or does not contain elements.
	 *
	 * @param coll
	 * @return
	 */
	public static <T> boolean isEmpty(Collection<T> coll) {
		return coll == null || coll.isEmpty();
	}

	/**
	 * Return true if collection is null or does not contain elements.
	 *
	 * @param coll
	 * @return
	 */
	public static <T> boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * Return true if collection is not null and contains elements.
	 *
	 * @param coll
	 * @return
	 */
	public static <T> boolean isNotEmpty(Collection<T> coll) {
		return coll != null && !coll.isEmpty();
	}

	/**
	 * Return true if collection is not null and contains elements.
	 *
	 * @param map
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}

	public static Map<String, String> toMap(List<String> list) {
		Map<String, String> map = new HashMap<>();
		for (String item : list) {
			map.put(item, item);
		}
		return map;
	}

	public static Map<String, String> toMap(String s) {
		Map<String, String> map = new HashMap<>();
		map.put(s, s);
		return map;
	}

	/**
	 * Return first item in the list if the list is not null and contains elements,
	 * otherwise null.
	 *
	 * @param list
	 * @return
	 */
	public static <T> T returnFirstOrNull(List<T> list) {
		return CollUtil.isNotEmpty(list) ? list.get(0) : null;
	}
}
