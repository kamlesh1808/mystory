/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity.view;

import com.jklprojects.mystory.business.advert.entity.Advert;
import com.jklprojects.mystory.business.advert.entity.AdvertStoryName;
import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.common.advert.AdvertStatus;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.story.StoryName;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents light weight view for AdvertAttr entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 3, 2018-11-23
 * @since 3, 2018-11-23
 */
public class AdvertStoryNameView extends AbstractAppView<AdvertStoryName> {

	private static final XLogger logger = XLoggerFactory.getXLogger(AdvertStoryNameView.class);

	private static final long serialVersionUID = 7557991033315424032L;

	private AdvertView advert;
	private StoryName storyName;
	private AdvertStatus status;

	public AdvertStatus getStatus() {
		return status;
	}

	public void setStatus(AdvertStatus status) {
		this.status = status;
	}

	public AdvertStoryNameView() {
		super(AdvertStoryName.class);
	}

	public static List<AdvertStoryNameView> toViews(List<AdvertStoryName> entities) {
		List<AdvertStoryNameView> views = new ArrayList<>();
		for (AdvertStoryName entity : entities) {
			views.add(new AdvertStoryNameView().setViewFromEntity(entity, true));
		}
		return views;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AdvertStoryNameView other = (AdvertStoryNameView) obj;
		return Objects.equals(advert, other.advert) && storyName == other.storyName;
	}

	public AdvertView getAdvert() {
		return advert;
	}

	public Integer getAdvertStoryNameId() {
		return getStoryName().getId();
	}

	public StoryName getStoryName() {
		return storyName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(advert, storyName);
	}

	public void setAdvert(AdvertView advert) {
		this.advert = advert;
	}

	@Override
	public AdvertStoryName setEntityFromView(AdvertStoryName entity, boolean setBi) throws AppCodeException {
		logger.entry(this);

		entity.setAdvert(getAdvert().setEntityFromView(new Advert(), false));
		entity.setStoryName(getStoryName());
		entity.setStatus(getStatus());

		return entity;
	}

	public void setStoryName(StoryName storyName) {
		this.storyName = storyName;
	}

	@Override
	public AdvertStoryNameView setViewFromEntity(AdvertStoryName entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		setAdvert(new AdvertView().setViewFromEntity(entity.getAdvert(), false));
		setStoryName(entity.getStoryName());
		setStatus(entity.getStatus());

		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdvertStoryNameView [getStatus()=").append(getStatus()).append(", getAdvert()=")
				.append(getAdvert()).append(", getStoryName()=").append(getStoryName()).append("]");
		return builder.toString();
	}
}
