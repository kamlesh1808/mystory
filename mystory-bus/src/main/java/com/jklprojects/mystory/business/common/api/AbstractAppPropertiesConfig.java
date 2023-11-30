/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.api;

import com.jklprojects.mystory.business.common.ejb.AppConfigBean;
import com.jklprojects.mystory.common.ex.AppRuntimeException;
import com.jklprojects.mystory.common.util.NumUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.annotation.PreDestroy;
import javax.ejb.Lock;
import javax.ejb.LockType;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Common properties handling functionality is provided in this base class.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-12-17
 * @version 1, 2017-11-23
 * @version 2, 2018-04-23
 */
public abstract class AbstractAppPropertiesConfig implements AppPropertiesConfig {

	private static final XLogger logger = XLoggerFactory.getXLogger(AppConfigBean.class);

	private Properties properties = new Properties();
	private long propsLastModifiedMilliSeconds = 0L;

	public AbstractAppPropertiesConfig() {
		super();

		loadProperties();
	}

	@PreDestroy
	public void cleanUp() {
		properties = null;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public long getPropertiesFileLastModified() {
		URL url = Thread.currentThread().getContextClassLoader().getResource(getPropertiesFileName());
		return new File(url.getFile()).lastModified();
	}

	@Override
	@Lock(LockType.READ)
	public String getProperty(String name) {
		return getProperties().getProperty(name);
	}

	@Override
	@Lock(LockType.READ)
	public int getProperty(String name, int defaultValue) {
		return NumUtil.parseInteger(getProperties().getProperty(name), defaultValue);
	}

	@Override
	@Lock(LockType.READ)
	public String getProperty(String name, String defaultValue) {
		return getProperties().getProperty(name, defaultValue);
	}

	@Override
	@Lock(LockType.READ)
	public boolean getPropertyBoolean(String name) {
		return Boolean.parseBoolean(getProperties().getProperty(name));
	}

	@Override
	public final void loadProperties() throws AppRuntimeException {
		logger.entry();

		logger.info("Loading properties file: {}", getPropertiesFileName());

		InputStream appConfigIS = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(getPropertiesFileName());

		properties.clear();
		properties = new Properties();
		try {
			properties.load(appConfigIS);

			propsLastModifiedMilliSeconds = getPropertiesFileLastModified();
			logger.info("Properties loaded: {} ", properties.toString());

			appConfigIS.close();
		} catch (IOException e) {
			throw new AppRuntimeException(e.getMessage(), e);
		}

		logger.exit();
	}

	public long getPropsLastModifiedMilliSeconds() {
		return propsLastModifiedMilliSeconds;
	}
}
