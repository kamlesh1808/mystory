/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.entity.table;

import com.jklprojects.mystory.business.api.entity.view.AbstractView;
import com.jklprojects.mystory.common.TableStatus;
import com.jklprojects.mystory.common.ex.AppCodeException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * CountryView view.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, July 5, 2015
 */
public class CountryView extends AbstractView<Country> implements Comparable<CountryView> {

	private static final long serialVersionUID = 4853381363948517409L;

	private static final XLogger logger = XLoggerFactory.getXLogger(CountryView.class);

	private String nameEn;
	private String alpha2Code;
	private String alpha3Code;
	private Integer numericCode;
	private String dialingCode;
	private String localName;
	private TableStatus status;

	public CountryView() {
		super(Country.class);
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getAlpha2Code() {
		return alpha2Code;
	}

	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}

	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}

	public Integer getNumericCode() {
		return numericCode;
	}

	public void setNumericCode(Integer numericCode) {
		this.numericCode = numericCode;
	}

	public String getDialingCode() {
		return dialingCode;
	}

	public void setDialingCode(String dialingCode) {
		this.dialingCode = dialingCode;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public TableStatus getStatus() {
		return status;
	}

	public void setStatus(TableStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CountryView))
			return false;

		CountryView that = (CountryView) o;

		if (getNameEn() != null ? !getNameEn().equals(that.getNameEn()) : that.getNameEn() != null)
			return false;
		if (getAlpha2Code() != null ? !getAlpha2Code().equals(that.getAlpha2Code()) : that.getAlpha2Code() != null)
			return false;
		if (getAlpha3Code() != null ? !getAlpha3Code().equals(that.getAlpha3Code()) : that.getAlpha3Code() != null)
			return false;
		return getNumericCode() != null
				? getNumericCode().equals(that.getNumericCode())
				: that.getNumericCode() == null;
	}

	@Override
	public int hashCode() {
		int result = getNameEn() != null ? getNameEn().hashCode() : 0;
		result = 31 * result + (getAlpha2Code() != null ? getAlpha2Code().hashCode() : 0);
		result = 31 * result + (getAlpha3Code() != null ? getAlpha3Code().hashCode() : 0);
		result = 31 * result + (getNumericCode() != null ? getNumericCode().hashCode() : 0);
		return result;
	}

	@Override
	public Country setEntityFromView(Country entity, boolean setBi) throws AppCodeException {

		entity.setAlpha2Code(this.getAlpha2Code());
		entity.setNameEn(this.getNameEn());

		return entity;
	}

	@Override
	public CountryView setViewFromEntity(Country entity, boolean setBi) {

		setAlpha2Code(entity.getAlpha2Code());
		setNameEn(entity.getNameEn());
		setLocalName(entity.getLocalName());
		setAlpha3Code(entity.getAlpha3Code());
		setDialingCode(entity.getAlpha3Code());
		setNumericCode(entity.getNumericCode());
		setStatus(entity.getStatus());

		return this;
	}

	public static List<CountryView> toViews(List<Country> entities) {
		List<CountryView> views = new ArrayList<>();
		for (Country e : entities) {
			views.add(new CountryView().setViewFromEntity(e, true));
		}
		return views;
	}

	/**
	 * Compare using name
	 *
	 * @param o
	 * @return
	 * @see String#compareTo(String)
	 */
	@Override
	public int compareTo(CountryView o) {
		return getNameEn() != null && o != null ? getNameEn().compareTo(o.getNameEn()) : 0;
	}
}
