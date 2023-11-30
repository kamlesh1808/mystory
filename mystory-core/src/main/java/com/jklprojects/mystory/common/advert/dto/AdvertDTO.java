/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.advert.dto;

import com.jklprojects.mystory.common.advert.AdvertPriorityType;
import com.jklprojects.mystory.common.advert.AdvertType;
import com.jklprojects.mystory.common.api.IAppDTO;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
public final class AdvertDTO implements IAppDTO {

	private static final long serialVersionUID = 263285339665499L;

	private long advertId;
	private String advertName;
	private AdvertType advertType;
	private AdvertPriorityType advertPriorityType;

	private String header;
	private String title;
	private String subTitle;
	private String link;
	private String imageSrc;

	public AdvertDTO() {
		super();
	}

	public AdvertDTO(long advertId, String advertName, AdvertType advertType, AdvertPriorityType advertPriorityType,
			String header, String title, String subTitle, String link, String imageSrc) {

		this();

		this.advertId = advertId;
		this.advertName = advertName;
		this.advertType = advertType;
		this.advertPriorityType = advertPriorityType;
		this.header = header;
		this.title = title;
		this.subTitle = subTitle;
		this.link = link;
		this.imageSrc = imageSrc;
	}

	public long getAdvertId() {
		return advertId;
	}

	public AdvertType getAdvertType() {
		return advertType;
	}

	public String getAdvertName() {
		return advertName;
	}

	public AdvertPriorityType getAdvertPriorityType() {
		return advertPriorityType;
	}

	public int getAdvertPriority() {
		return advertPriorityType.getPriority();
	}

	public String getHeader() {
		return header;
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getLink() {
		return link;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	@Override
	public String toString() {
		return "AdvertDTO{" + "advertId=" + advertId + ", advertName='" + advertName + '\'' + ", advertType="
				+ advertType + ", advertPriorityType=" + advertPriorityType + ", header='" + header + '\'' + ", title='"
				+ title + '\'' + ", subTitle='" + subTitle + '\'' + ", link='" + link + '\'' + ", imageSrc='" + imageSrc
				+ '\'' + '}';
	}
}
