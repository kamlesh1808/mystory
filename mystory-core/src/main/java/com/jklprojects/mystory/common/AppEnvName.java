/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common;

import java.util.Arrays;

/**
 * Application Environment Name defines a set of environment names.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-12-23
 * @version 2, 2018-04-23
 * @version 2, 2018-06-23
 */
public enum AppEnvName {
	DEV("DEV"), PROD("PROD"), QA("QA"), UAT("UAT");

	private final String name;

	AppEnvName(String name) {
		this.name = name;
	}

	public static AppEnvName toAppEnvName(String name) {
		return Arrays.stream(AppEnvName.values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().get();
	}

	public String getName() {
		return name;
	}

	public boolean isDEV() {
		return this == DEV;
	}

	public boolean isPROD() {
		return this == PROD;
	}

	public boolean isQA() {
		return this == QA;
	}

	public boolean isUAT() {
		return this == UAT;
	}
}
