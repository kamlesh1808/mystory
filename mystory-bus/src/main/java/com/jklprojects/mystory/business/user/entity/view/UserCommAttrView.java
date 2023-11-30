/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.user.entity.UserCommAttr;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.user.UserCommAttrName;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2016-01-23
 */
public class UserCommAttrView extends AbstractAppView<UserCommAttr> {
	private static final long serialVersionUID = 613234582278986674L;

	private static final XLogger logger = XLoggerFactory.getXLogger(UserCommAttrView.class);

	private UserCommAttrName attrName;
	private String value;

	public UserCommAttrView() {
		super(UserCommAttr.class);
	}

	@Override
	public UserCommAttrView setViewFromEntity(UserCommAttr entity, boolean setBi) {
		super.setViewFromEntity(entity, setBi);

		setAttrName(entity.getAttrName());
		setValue(entity.getValue());

		return this;
	}

	@Override
	public UserCommAttr setEntityFromView(UserCommAttr entity, boolean setBi) throws AppCodeException {

		entity.setAttrName(getAttrName());
		entity.setValue(getValue());

		return entity;
	}

	public UserCommAttrName getAttrName() {
		return attrName;
	}

	public void setAttrName(UserCommAttrName attrName) {
		this.attrName = attrName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "UserCommAttrView{" + "attrName=" + attrName + ", value='" + value + '\'' + "} " + super.toString();
	}

	@Override
	public int hashCode() {
		int result = getAttrName() != null ? getAttrName().hashCode() : 0;
		result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof UserCommAttrView))
			return false;

		UserCommAttrView that = (UserCommAttrView) o;

		if (getAttrName() != that.getAttrName())
			return false;
		return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
	}
}
