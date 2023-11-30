/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.rs;

import com.jklprojects.mystory.business.api.rs.rto.RTO;
import com.jklprojects.mystory.common.AppEnvName;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-04-23
 * @version 3, 2018-10-23
 * @version 3, 2018-11-23
 */
@XmlRootElement
public class AbstractRTO implements RTO {
	private static final long serialVersionUID = 1993902564696469141L;

	public static final String APPLICATION_PATH = "/rest";

	private AppEnvName appEnvName;

	private String restAPIBaseURI;

	public AbstractRTO() {
		super();
	}

	public AbstractRTO(String restAPIBaseURI) {
		this.restAPIBaseURI = restAPIBaseURI;
	}

	public AppEnvName getAppEnvName() {
		return appEnvName;
	}

	public String getRestAPIBaseURI() {
		return restAPIBaseURI;
	}

	public void setAppEnvName(AppEnvName appEnvName) {
		this.appEnvName = appEnvName;
	}

	public void setRestAPIBaseURI(String restAPIBaseURI) {
		this.restAPIBaseURI = restAPIBaseURI;
	}
}
