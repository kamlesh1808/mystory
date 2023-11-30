/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity.view;

import com.jklprojects.mystory.business.advert.entity.Advert;
import com.jklprojects.mystory.business.advert.entity.AdvertAttr;
import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.common.advert.AdvertAttrType;
import com.jklprojects.mystory.common.ex.AppCodeException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents light weight view for AdvertAttr entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2017-JAN-01
 */
public class AdvertAttrView extends AbstractAppView<AdvertAttr> {
	private static final long serialVersionUID = 1799609060219972021L;

	private static final XLogger logger = XLoggerFactory.getXLogger(AdvertAttrView.class);

	private AdvertAttrType advertAttrType;
	private AdvertView advert;
	private String value;

	public AdvertAttrView() {
		super(AdvertAttr.class);
	}

	public static List<AdvertAttrView> toViews(List<AdvertAttr> entities) {
		List<AdvertAttrView> views = new ArrayList<>();
		for (AdvertAttr entity : entities) {
			views.add(new AdvertAttrView().setViewFromEntity(entity, true));
		}
		return views;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof AdvertAttrView))
			return false;

		AdvertAttrView that = (AdvertAttrView) o;

		if (getAdvertAttrType() != that.getAdvertAttrType())
			return false;
		return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
	}

	@Override
	public int hashCode() {
		int result = getAdvertAttrType() != null ? getAdvertAttrType().hashCode() : 0;
		result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
		return result;
	}

	public AdvertView getAdvert() {
		return advert;
	}

	public AdvertAttrType getAdvertAttrType() {
		return advertAttrType;
	}

	public String getValue() {
		return value;
	}

	public void setAdvert(AdvertView advert) {
		this.advert = advert;
	}

	public void setAdvertAttrType(AdvertAttrType advertAttrType) {
		this.advertAttrType = advertAttrType;
	}

	@Override
	public AdvertAttr setEntityFromView(AdvertAttr entity, boolean setBi) throws AppCodeException {
		logger.entry(this);

		entity.setAdvertAttrType(getAdvertAttrType());
		entity.setAdvert(getAdvert().setEntityFromView(new Advert(), false));
		entity.setValue(getValue());

		return entity;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public AdvertAttrView setViewFromEntity(AdvertAttr entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		setAdvert(new AdvertView().setViewFromEntity(entity.getAdvert(), false));
		setAdvertAttrType(entity.getAdvertAttrType());
		setValue(entity.getValue());

		return this;
	}

	@Override
	public String toString() {
		return "AdvertAttrView{" + "advertAttrType=" + advertAttrType + ", advert=" + advert + ", value='" + value
				+ '\'' + "} " + super.toString();
	}
}
