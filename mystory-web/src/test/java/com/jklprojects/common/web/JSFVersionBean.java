/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.common.web;

import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("jSFVersionBean")
public class JSFVersionBean {
	private final String version;

	public JSFVersionBean() {
		version = FacesContext.class.getPackage().getImplementationVersion();
	}

	public String getVersion() {
		return version;
	}
}
