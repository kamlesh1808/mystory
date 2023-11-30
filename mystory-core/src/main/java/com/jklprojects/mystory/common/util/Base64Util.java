/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Oct-23
 */
public class Base64Util {

	/**
	 * Encode file to Base64 {@link String}.
	 *
	 * @param file
	 * @return map of file name with file content as Base64 encoded {@link String}
	 * @throws IOException
	 */
	public static String encodeToBase64String(File file) throws IOException {
		String base64String = "";

		if (file != null) {
			try (FileInputStream fis = new FileInputStream(file)) {
				byte[] data = new byte[(int) file.length()];
				if (fis.read(data) > 0) {
					base64String = Base64.getEncoder().encodeToString(data);
				}
			}
		}
		return base64String;
	}

	/**
	 * Encode files to Base64 {@link String} and return map of file names with file
	 * content as Base64 encoded {@link String}
	 *
	 * @param files
	 * @return map of file names with file content as Base64 encoded {@link String}
	 * @throws IOException
	 */
	public static Map<String, String> encodeToBase64String(List<File> files) throws IOException {
		Map<String, String> base64EncodedFiles = new HashMap<>();

		for (File file : files) {
			if (file != null) {
				base64EncodedFiles.put(file.getAbsolutePath(), encodeToBase64String(file));
			}
		}
		return base64EncodedFiles;
	}
}
