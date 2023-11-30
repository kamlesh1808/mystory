/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.i18n;

import com.jklprojects.mystory.common.ex.AppCodeException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents i18n utility API to retrieve i18n messages from resource bundles
 * configured in faces-config.xml
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Apr 25, 2015
 */
public enum I18N {
	APP("i18nApp", "i18n.application"), APP_MSGS("i18nAppMsgs", "i18n.app_messages"), APP_ENUMS("i18nAppEnums",
			"i18n.app_enums"), REPORT("i18nReport", "i18n.report");

	private static final XLogger logger = XLoggerFactory.getXLogger(I18N.class);

	private final String baseName;

	// the var is as defined in the faces-config.xml
	private final String var;

	private ResourceBundle rb;

	I18N(String var, String baseName) {
		this.baseName = baseName;
		this.var = var;
	}

	public String getBaseName() {
		return baseName;
	}

	public String getVar() {
		return var;
	}

	private ResourceBundle getRB() {
		if (rb == null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			if (fc != null) {
				rb = fc.getApplication().getResourceBundle(fc, getVar());
			}

			if (rb == null) {
				rb = getRBUsingBaseName();
			}
		}
		return rb;
	}

	public ResourceBundle getRBUsingBaseName() {
		return rb == null ? rb = ResourceBundle.getBundle(getBaseName()) : rb;
	}

	/**
	 * Return i18n value from this resource bundle for the key. If resource key is
	 * not found or is null, an {@code String} is returned.
	 *
	 * @param key
	 *            i18n resource key
	 * @return i18n resource value for key.
	 */
	public String getI18N(String key) {
		try {
			return getRB().getString(key);
		} catch (MissingResourceException | NullPointerException e) {
			logger.catching(e);
		}
		return "";
	}

	/**
	 * Return i18n value from this I18N resource bundle and format it with the
	 * message parameters.
	 *
	 * @param key
	 * @param messageParams
	 * @return formatted i18n resource value
	 * @see #getI18N(String)
	 * @see MessageFormat#format(String, Object...)
	 */
	public String getI18N(String key, Object... messageParams) {
		return MessageFormat.format(getI18N(key), messageParams);
	}

	/**
	 * Return i18n value from the resource bundle using the error code of the
	 * exception as the key. Format the resource value with the message parameters
	 * contained in the exception. If the exception's error code is null or empty
	 * return the exception's message.
	 *
	 * @param e
	 * @return formatted i18n resource value
	 * @see #getI18N(String, Object...)
	 */
	public String getI18N(AppCodeException e) {
		return (e.isErrorCodeValid()) ? getI18N(e.getErrorCode(), e.getMessageParams()) : e.getMessage();
	}

	/**
	 * Return i18n value from the resource bundle using the specified parameters as
	 * the key concatenated by a dot. For examples, StoryName.0.UNDEFINED=Select One
	 *
	 * @param simpleClassName
	 * @param id
	 * @param name
	 * @return i18n resource value for key.
	 */
	public String getI18N(String simpleClassName, long id, String name) {
		return getI18N(simpleClassName + "." + id + "." + name);
	}
}
