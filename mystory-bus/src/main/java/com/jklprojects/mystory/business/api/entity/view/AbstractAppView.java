/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity.view;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-05-01
 * @version 1, 2017-11-23
 * @version 2, 2017-12-23
 * @version 2, 2018-03-23
 */
public abstract class AbstractAppView<E extends AbstractAppEntity> extends AbstractIDView<E> {

	public static final String SHORT_DATE = "MMM d, yyyy";
	public static final String SHORT_DATE_TIME = "MMM d, yyyy HH:mm:ss";

	private static final long serialVersionUID = -5475669697772839721L;

	private OffsetDateTime createdTimestamp;
	private OffsetDateTime updatedTimestamp;

	/**
	 * @param entityClass
	 */
	public AbstractAppView(Class<E> entityClass) {
		super(entityClass);
	}

	public String getCreatedMonth() {
		return createdTimestamp != null ? String.format("%02d", createdTimestamp.getMonth().getValue()) : "";
	}

	public String getCreatedDayOfMonth() {
		return createdTimestamp != null ? String.format("%02d", createdTimestamp.getDayOfMonth()) : "";
	}

	public OffsetDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public String getCreatedTimestampFormatted() {
		return createdTimestamp != null ? DateTimeFormatter.RFC_1123_DATE_TIME.format(createdTimestamp) : "";
	}

	public String getCreatedTimestampFormattedISOLocalDate() {
		return createdTimestamp != null ? DateTimeFormatter.ISO_LOCAL_DATE.format(createdTimestamp) : "";
	}

	public String getCreatedTimestampFormattedISOLocalDateTime() {
		return createdTimestamp != null ? DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(createdTimestamp) : "";
	}

	public String getCreatedTimestampFormattedRFC1123DateTime() {
		return createdTimestamp != null ? DateTimeFormatter.RFC_1123_DATE_TIME.format(createdTimestamp) : "";
	}

	public String getCreatedTimestampFormattedShortDate() {
		return createdTimestamp != null ? DateTimeFormatter.ofPattern(SHORT_DATE).format(createdTimestamp) : "";
	}

	public String getCreatedTimestampFormattedShortDateTime() {
		return createdTimestamp != null ? DateTimeFormatter.ofPattern(SHORT_DATE_TIME).format(createdTimestamp) : "";
	}

	public int getCreatedYear() {
		return createdTimestamp != null ? createdTimestamp.getYear() : -1;
	}

	public OffsetDateTime getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public String getUpdatedTimestampFormatted() {
		return updatedTimestamp != null ? DateTimeFormatter.RFC_1123_DATE_TIME.format(updatedTimestamp) : "";
	}

	public String getUpdatedTimestampFormattedISOLocalDate() {
		return updatedTimestamp != null ? DateTimeFormatter.ISO_LOCAL_DATE.format(updatedTimestamp) : "";
	}

	public String getUpdatedTimestampFormattedISOLocalDateTime() {
		return updatedTimestamp != null ? DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(updatedTimestamp) : "";
	}

	public String getUpdatedTimestampFormattedShortDate() {
		return updatedTimestamp != null ? DateTimeFormatter.ofPattern(SHORT_DATE).format(updatedTimestamp) : "";
	}

	public String getUpdatedTimestampFormattedShortDateTime() {
		return updatedTimestamp != null ? DateTimeFormatter.ofPattern(SHORT_DATE_TIME).format(updatedTimestamp) : "";
	}

	public void setCreatedTimestamp(OffsetDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public void setUpdatedTimestamp(OffsetDateTime updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	/**
	 * Return the updated view based on the given entity.
	 *
	 * @param entity
	 * @param setBi
	 * @return updated view
	 */
	@Override
	public AbstractAppView<E> setViewFromEntity(E entity, boolean setBi) {
		super.setViewFromEntity(entity, setBi);

		this.setCreatedTimestamp(entity.getCreatedUpdated().getCreatedTimestamp());
		this.setUpdatedTimestamp(entity.getCreatedUpdated().getUpdatedTimestamp());

		return this;
	}
}
