/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.common.entity.table.Country;
import com.jklprojects.mystory.business.common.entity.table.CountryView;
import com.jklprojects.mystory.business.user.entity.ContactUs;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.user.ContactUsStatus;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents light weight view for ContactUs entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-07-29
 */
public class ContactUsView extends AbstractAppView<ContactUs> implements Comparable<ContactUsView> {
	private static final long serialVersionUID = 1887719397936715243L;

	private static final XLogger logger = XLoggerFactory.getXLogger(ContactUsView.class);

	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String contactUsText;
	private CountryView country;
	private ContactUsStatus status;

	public ContactUsView() {
		super(ContactUs.class);
	}

	public static List<ContactUsView> toViews(List<ContactUs> entities) {
		List<ContactUsView> views = new ArrayList<>();
		for (ContactUs entity : entities) {
			views.add(new ContactUsView().setViewFromEntity(entity, true));
		}
		return views;
	}

	@Override
	public String toString() {
		return "ContactUsView{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='"
				+ email + '\'' + ", country=" + country + ", status=" + status + "} " + super.toString();
	}

	@Override
	public ContactUsView setViewFromEntity(ContactUs entity, boolean setBi) {
		super.setViewFromEntity(entity, setBi);

		setFirstName(entity.getFirstName());
		setLastName(entity.getLastName());
		setUserName(entity.getUserName());
		setEmail(entity.getEmail());
		setContactUsText(entity.getContactUsText());
		setStatus(entity.getStatus());

		setCountry(new CountryView().setViewFromEntity(entity.getCountry(), false));

		return this;
	}

	@Override
	public ContactUs setEntityFromView(ContactUs entity, boolean setBi) throws AppCodeException {
		entity.setFirstName(getFirstName());
		entity.setLastName(getLastName());
		entity.setUserName(getUserName());
		entity.setEmail(getEmail());
		entity.setContactUsText(getContactUsText());

		if (getStatus() != null) {
			entity.setStatus(getStatus());
		}

		if (getCountry() != null) {
			entity.setCountry(getCountry().setEntityFromView(new Country(), false));
		}

		return entity;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactUsText() {
		return contactUsText;
	}

	public void setContactUsText(String contactUsText) {
		this.contactUsText = contactUsText;
	}

	public CountryView getCountry() {
		return country;
	}

	public void setCountry(CountryView country) {
		this.country = country;
	}

	public ContactUsStatus getStatus() {
		return status;
	}

	public void setStatus(ContactUsStatus status) {
		this.status = status;
	}

	/**
	 * Compare with created timestamp.
	 *
	 * @param o
	 */
	@Override
	public int compareTo(ContactUsView o) {
		return getCreatedTimestamp().compareTo(o.getCreatedTimestamp());
	}

	@Override
	public int hashCode() {
		int result = getFirstName() != null ? getFirstName().hashCode() : 0;
		result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
		result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
		result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
		result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ContactUsView that = (ContactUsView) o;

		if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
			return false;
		if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
			return false;
		if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null)
			return false;
		if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null)
			return false;
		return getStatus() == that.getStatus();
	}
}
