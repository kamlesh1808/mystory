/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.auth;

import com.jklprojects.mystory.common.oauth2.IOAuth2Consts;

/**
 * {@link IOAuth2Consts#GOOGLE_USERINFO_V2_ME_URL}
 * {@link IOAuth2Consts#GOOGLE_SCOPE_PROFILE}
 *
 * <pre>
*
*
* {
* "id": "112910682927731272105",
* "email": "joyousjavadev@gmail.com",
* "verified_email": true,
* "name": "joyous javadev",
* "given_name": "joyous",
* "family_name": "javadev",
* "link": "https://plus.google.com/112910682927731272105",
* "picture": "https://lh3.googleusercontent.com/-XdUIqdMkCWA/AAAAAAAAAAI/AAAAAAAAAAA/4252rscbv5M/photo.jpg",
* "locale": "en"
* }
 * </pre>
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-Dec-23
 */
public class GUserInfo extends OAuth2Bean {
	private static final long serialVersionUID = -5945933639208115409L;

	private String email;
	private String verified_email;
	private String name;
	private String given_name;
	private String family_name;
	private String link;
	private String picture;
	private String locale;

	public GUserInfo() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public String getFamily_name() {
		return family_name;
	}

	public String getGiven_name() {
		return given_name;
	}

	public String getLink() {
		return link;
	}

	public String getLocale() {
		return locale;
	}

	public String getName() {
		return name;
	}

	public String getPicture() {
		return picture;
	}

	public String getVerified_email() {
		return verified_email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setVerified_email(String verified_email) {
		this.verified_email = verified_email;
	}

	@Override
	public String toString() {
		return "GUserInfo [" + (email != null ? "email=" + email + ", " : "")
				+ (verified_email != null ? "verified_email=" + verified_email + ", " : "")
				+ (name != null ? "name=" + name + ", " : "")
				+ (given_name != null ? "given_name=" + given_name + ", " : "")
				+ (family_name != null ? "family_name=" + family_name + ", " : "")
				+ (link != null ? "link=" + link + ", " : "") + (picture != null ? "picture=" + picture + ", " : "")
				+ (locale != null ? "locale=" + locale + ", " : "") + (getId() != null ? "getId()=" + getId() : "")
				+ "]";
	}
}
