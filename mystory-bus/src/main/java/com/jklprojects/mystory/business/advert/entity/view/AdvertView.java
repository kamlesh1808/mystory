/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity.view;

import com.jklprojects.mystory.business.advert.entity.Advert;
import com.jklprojects.mystory.business.advert.entity.AdvertAttr;
import com.jklprojects.mystory.business.advert.entity.AdvertStoryName;
import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.common.advert.AdvertAttrType;
import com.jklprojects.mystory.common.advert.AdvertPriorityType;
import com.jklprojects.mystory.common.advert.AdvertStatus;
import com.jklprojects.mystory.common.advert.AdvertType;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.util.CollUtil;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Represents light weight view for Advert entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-04-23
 * @since 1, 2017-02-10
 */
public class AdvertView extends AbstractAppView<Advert> {
	private static final XLogger logger = XLoggerFactory.getXLogger(AdvertView.class);

	private static final long serialVersionUID = -4737816251404495293L;

	private String name;
	private String description;
	private AdvertType advertType;
	private AdvertPriorityType advertPriorityType;
	private AdvertStatus status;
	private OffsetDateTime startTimestamp;
	private OffsetDateTime endTimestamp;

	private List<AdvertAttrView> advertAttrs = new ArrayList<>();
	private List<AdvertStoryNameView> advertStoryNames = new ArrayList<>();

	public AdvertView() {
		super(Advert.class);

		setStatus(AdvertStatus.NEW);
	}

	public static List<AdvertView> toViews(List<Advert> entities) {
		List<AdvertView> views = new ArrayList<>();
		for (Advert entity : entities) {
			views.add(new AdvertView().setViewFromEntity(entity, true));
		}
		return views;
	}

	public void addAdvertAttr(AdvertAttrView advertAttr) {
		logger.entry(advertAttr);

		if (!advertAttrs.contains(advertAttr)) {
			advertAttrs.add(advertAttr);
		}
	}

	public void addAdvertStoryName(AdvertStoryNameView adverStoryName) {
		if (!advertStoryNames.contains(adverStoryName)) {
			advertStoryNames.add(adverStoryName);
		}
	}

	/**
	 * @deprecated
	 *
	 * @param advertAttrType
	 * @return
	 */
	@Deprecated
	public AdvertAttrView getAdvertAttr(AdvertAttrType advertAttrType) {
		return advertAttrs.stream().filter(aa -> aa.getAdvertAttrType() == advertAttrType).findFirst().get();
	}

	public String getAdvertAttrValue(AdvertAttrType advertAttrType) {
		Optional<AdvertAttrView> aav = advertAttrs.stream().filter(v -> v.getAdvertAttrType() == advertAttrType)
				.findFirst();
		return aav.isPresent() ? aav.get().getValue() : "";
	}

	public String getAdvertAttrHeaderValue() {
		return getAdvertAttrValue(AdvertAttrType.HEADER);
	}

	public String getAdvertAttrImagePathLocalValue() {
		return getAdvertAttrValue(AdvertAttrType.IMAGE_PATH_LOCAL);
	}

	public String getAdvertAttrImageSrcValue() {
		return getAdvertAttrValue(AdvertAttrType.IMAGE_SRC);
	}

	public String getAdvertAttrLinkValue() {
		return getAdvertAttrValue(AdvertAttrType.LINK);
	}

	public List<AdvertAttrView> getAdvertAttrs() {
		return advertAttrs;
	}

	public String getAdvertAttrSubTitleValue() {
		return getAdvertAttr(AdvertAttrType.SUB_TITLE).getValue();
	}

	public String getAdvertAttrTitleValue() {
		return getAdvertAttr(AdvertAttrType.TITLE).getValue();
	}

	public String getAdvertAttrTooltipValue() {
		return getAdvertAttr(AdvertAttrType.TOOLTIP).getValue();
	}

	public AdvertPriorityType getAdvertPriorityType() {
		return advertPriorityType;
	}

	public int getAdvertPriority() {
		return advertPriorityType.getPriority();
	}

	public AdvertType getAdvertType() {
		return advertType;
	}

	public String getDescription() {
		return description;
	}

	public OffsetDateTime getEndTimestamp() {
		return endTimestamp;
	}

	public String getName() {
		return name;
	}

	public OffsetDateTime getStartTimestamp() {
		return startTimestamp;
	}

	public AdvertStatus getStatus() {
		return status;
	}

	public boolean isActive() {
		return AdvertStatus.ACTIVE == getStatus();
	}

	public boolean isAdvertTypeBook() {
		return AdvertType.BOOK == getAdvertType();
	}

	public boolean isAdvertTypeProduct() {
		return AdvertType.PRODUCT == getAdvertType();
	}

	public boolean isAdvertTypeWebsite() {
		return AdvertType.WEBSITE == getAdvertType();
	}

	public boolean isInActive() {
		return AdvertStatus.INACTIVE == getStatus();
	}

	public void setAdvertAttrs(List<AdvertAttrView> advertAttrs) {
		this.advertAttrs = advertAttrs;
	}

	public void setAdvertPriorityType(AdvertPriorityType advertPriorityType) {
		this.advertPriorityType = advertPriorityType;
	}

	public void setAdvertType(AdvertType advertType) {
		this.advertType = advertType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndTimestamp(OffsetDateTime endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(advertType, description, name);
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
		AdvertView other = (AdvertView) obj;
		return advertType == other.advertType && Objects.equals(description, other.description)
				&& Objects.equals(name, other.name);
	}

	@Override
	public Advert setEntityFromView(Advert entity, boolean setBi) throws AppCodeException {
		logger.entry(this);

		entity.setName(getName());
		entity.setDescription(getDescription());
		entity.setAdvertType(getAdvertType());
		entity.setAdvertPriorityType(getAdvertPriorityType());
		entity.setStatus(getStatus());

		entity.setStartTimestamp(getStartTimestamp());
		entity.setEndTimestamp(getEndTimestamp());

		if (setBi) {
			if (CollUtil.isNotEmpty(getAdvertAttrs())) {
				for (AdvertAttrView v : getAdvertAttrs()) {
					entity.addAdvertAttr(v.setEntityFromView(new AdvertAttr(), false));
				}
			}

			if (CollUtil.isNotEmpty(getAdvertStoryNames())) {
				for (AdvertStoryNameView v : getAdvertStoryNames()) {
					entity.addAdvertStoryName(v.setEntityFromView(new AdvertStoryName(), false));
				}
			}
		}

		return entity;
	}

	public List<AdvertStoryNameView> getAdvertStoryNames() {
		return advertStoryNames;
	}

	public void setAdvertStoryNames(List<AdvertStoryNameView> advertStoryNames) {
		this.advertStoryNames = advertStoryNames;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartTimestamp(OffsetDateTime startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public void setStatus(AdvertStatus status) {
		this.status = status;
	}

	@Override
	public AdvertView setViewFromEntity(Advert entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		setName(entity.getName());
		setDescription(entity.getDescription());
		setAdvertType(entity.getAdvertType());
		setAdvertPriorityType(entity.getAdvertPriorityType());
		setStatus(entity.getStatus());

		setStartTimestamp(entity.getStartTimestamp());
		setEndTimestamp(entity.getEndTimestamp());

		if (setBi) {
			if (CollUtil.isNotEmpty(entity.getAdvertAttrs())) {
				for (AdvertAttr en : entity.getAdvertAttrs()) {
					addAdvertAttr(new AdvertAttrView().setViewFromEntity(en, false));
				}
			}

			if (CollUtil.isNotEmpty(entity.getAdvertStoryNames())) {
				for (AdvertStoryName en : entity.getAdvertStoryNames()) {
					addAdvertStoryName(new AdvertStoryNameView().setViewFromEntity(en, false));
				}
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return "AdvertView{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", advertType="
				+ advertType + ", advertPriorityType=" + advertPriorityType + ", status=" + status + ", startTimestamp="
				+ startTimestamp + ", endTimestamp=" + endTimestamp + ", advertAttrs=" + advertAttrs
				+ ", advertStoryNames=" + advertStoryNames + '}';
	}
}
