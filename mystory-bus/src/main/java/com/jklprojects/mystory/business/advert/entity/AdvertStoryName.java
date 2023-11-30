/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.story.entity.converter.StoryNameAttrConverter;
import com.jklprojects.mystory.common.advert.AdvertStatus;
import com.jklprojects.mystory.common.story.StoryName;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 3, 2018-11-23
 */
@Entity
@Table(name = "F_ADVERT_STORY_NAME")
@NamedQuery(name = "findAdvertStoryNameAll", query = "SELECT a FROM AdvertStoryName a")
public class AdvertStoryName extends AbstractAppEntity {

	private static final long serialVersionUID = -1751082291079540584L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_advert_id")
	private Advert advert;

	@Column(name = "story_name_id")
	@Convert(converter = StoryNameAttrConverter.class)
	private StoryName storyName;

	@Column(name = "status")
	private AdvertStatus status;

	public AdvertStoryName() {
		super();
	}

	public Advert getAdvert() {
		return advert;
	}

	public void setAdvert(Advert advert) {
		this.advert = advert;
	}

	public AdvertStatus getStatus() {
		return status;
	}

	public void setStatus(AdvertStatus status) {
		this.status = status;
	}

	public StoryName getStoryName() {
		return storyName;
	}

	public void setStoryName(StoryName storyName) {
		this.storyName = storyName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(advert, storyName);
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
		AdvertStoryName other = (AdvertStoryName) obj;
		return Objects.equals(advert, other.advert) && storyName == other.storyName;
	}

	@Override
	public String toString() {
		return "AdvertStoryName{" + "storyName=" + storyName + ", status=" + status + "} " + super.toString();
	}
}
