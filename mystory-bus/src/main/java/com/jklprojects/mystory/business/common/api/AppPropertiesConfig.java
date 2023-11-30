/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.api;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-12-17
 * @version 1, 2017-11-23
 * @version 2, 2018-04-23
 */
public interface AppPropertiesConfig {

	Properties getProperties();

	long getPropertiesFileLastModified();

	String getPropertiesFileName();

	String getProperty(String name);

	int getProperty(String name, int defaultValue);

	String getProperty(String name, String defaultValue);

	boolean getPropertyBoolean(String name);

	void loadProperties() throws IOException;

	void reloadProperties() throws IOException;
}
