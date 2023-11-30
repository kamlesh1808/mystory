/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.report;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-May-23
 */
public enum ReportOutputFormat {
	HTML("HTML", "HTML", ".html"), PDF("PDF", "PDF", ".pdf");

	private final String name;
	private final String description;
	private String fileExtension;

	ReportOutputFormat(String name, String description) {
		this.name = name;
		this.description = description;
	}

	ReportOutputFormat(String name, String description, String fileExtension) {
		this.name = name;
		this.description = description;
		this.fileExtension = fileExtension;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * Return true if the object equals one in the given list, false otherwise.
	 *
	 * @param list
	 * @return true if object equals one in the given list, false otherwise.
	 */
	public boolean in(ReportOutputFormat... list) {
		for (ReportOutputFormat obj : list) {
			if (this == obj) {
				return true;
			}
		}
		return false;
	}
}
