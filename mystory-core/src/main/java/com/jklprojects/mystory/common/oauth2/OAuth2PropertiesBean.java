/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.oauth2;

import java.io.Serializable;

/**
 * Immutable bean properties for OAuth2.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Dec-23
 */
public final class OAuth2PropertiesBean implements Serializable {

	private static final long serialVersionUID = -2386581304966902029L;

	private String callbackURI = "";
	private String clientId = "";
	private String clientSecret = "";

	/**
	 * @param callbackURI
	 * @param clientId
	 * @param clientSecret
	 */
	public OAuth2PropertiesBean(String callbackURI, String clientId, String clientSecret) {
		super();

		this.callbackURI = callbackURI;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	public String getCallbackURI() {
		return callbackURI;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
}
