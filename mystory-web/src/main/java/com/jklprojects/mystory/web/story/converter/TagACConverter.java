/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.story.converter;

import com.jklprojects.mystory.business.story.entity.view.TagView;
import com.jklprojects.mystory.web.story.bean.TagDataSource;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 * @see http://stackoverflow.com/a/7665768 for @ManagedProperty issue
 *      in @FacesConverter
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-05-23
 * @version 4, 2019-03-23
 */
@javax.faces.bean.ManagedBean(name = "tagACConverter")
@RequestScoped
public class TagACConverter implements Converter<TagView> {

	/*
	 * The use of pre-JEE 8 @javax.faces.bean.ManagedBean(name = "tagACConverter")
	 * is required for the converter to work
	 */

	@Inject
	private TagDataSource tagDS;

	public TagACConverter() {
		super();
	}

	@Override
	public TagView getAsObject(FacesContext context, UIComponent component, String value) {

		for (TagView tag : tagDS.getTags()) {
			if (tag.getName().equals(value)) {
				return tag;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, TagView tag) {
		return tag.getName();
	}
}
