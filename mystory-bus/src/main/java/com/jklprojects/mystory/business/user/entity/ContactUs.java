/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.common.entity.table.Country;
import com.jklprojects.mystory.business.user.entity.converter.ContactUsStatusAttrConverter;
import com.jklprojects.mystory.common.user.ContactUsStatus;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Contact Us entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-JUL-29
 */
@Entity
@Table(name = "A_CONTACTUS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
		@NamedQuery(name = "findContactUsWithStatus", query = "SELECT c FROM ContactUs c where c.status IN (:status) ORDER BY c.createdUpdated.createdTimestamp DESC")})
public class ContactUs extends AbstractAppEntity implements Comparable<ContactUs> {
	private static final long serialVersionUID = -3944992988068785075L;

	private static final XLogger logger = XLoggerFactory.getXLogger(ContactUs.class);

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "username")
	private String userName;

	@Column(name = "email")
	private String email;

	@OneToOne(optional = false)
	@JoinColumn(name = "country", nullable = false)
	private Country country;

	@Column(name = "contactus_text")
	private String contactUsText;

	@Column(name = "status")
	@Convert(converter = ContactUsStatusAttrConverter.class)
	private ContactUsStatus status;

	public ContactUs() {
		super();

		status = ContactUsStatus.NEW;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getContactUsText() {
		return contactUsText;
	}

	public void setContactUsText(String contactUsText) {
		this.contactUsText = contactUsText;
	}

	public ContactUsStatus getStatus() {
		return status;
	}

	public void setStatus(ContactUsStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ContactUs))
			return false;

		ContactUs contactUs = (ContactUs) o;

		if (getFirstName() != null
				? !getFirstName().equals(contactUs.getFirstName())
				: contactUs.getFirstName() != null)
			return false;
		if (getLastName() != null ? !getLastName().equals(contactUs.getLastName()) : contactUs.getLastName() != null)
			return false;
		if (getEmail() != null ? !getEmail().equals(contactUs.getEmail()) : contactUs.getEmail() != null)
			return false;
		if (getCountry() != null ? !getCountry().equals(contactUs.getCountry()) : contactUs.getCountry() != null)
			return false;
		return getStatus() == contactUs.getStatus();
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
	public String toString() {
		return "ContactUs{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", userName='"
				+ userName + '\'' + ", email='" + email + '\'' + ", country=" + country + ", contactUsText='"
				+ contactUsText + '\'' + ", status=" + status + "} " + super.toString();
	}

	/**
	 * Compare with created timestamp.
	 *
	 * @param o
	 */
	@Override
	public int compareTo(ContactUs o) {
		return getCreatedUpdated().getCreatedTimestamp().compareTo(o.getCreatedUpdated().getCreatedTimestamp());
	}
}
