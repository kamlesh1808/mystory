/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.advert.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.common.advert.AdvertAttrType;
import com.jklprojects.mystory.common.advert.AdvertPriorityType;
import com.jklprojects.mystory.common.advert.AdvertStatus;
import com.jklprojects.mystory.common.advert.AdvertType;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2017-01-23
 */
@Entity
@Table(name = "F_ADVERT")
@NamedQueries({
		@NamedQuery(name = "findAdvertAll", query = "SELECT a FROM Advert a " + "ORDER BY a.advertPriorityType, "
				+ "a.createdUpdated.createdTimestamp DESC, a.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findAdvertsWithStoryName", query = "SELECT a FROM Advert a "
				+ "INNER JOIN FETCH a.advertAttrs aa " + "WHERE a.advertType IN :advertTypes AND a.status IN :status "
				+ "AND (aa.advertAttrType IN :advertAttrTypes AND aa.value = :storyNameId) "
				+ "ORDER BY a.advertPriorityType DESC, "
				+ "a.createdUpdated.createdTimestamp DESC, a.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findAdvertsWithAdvertTypes", query = "SELECT a FROM Advert a "
				+ "INNER JOIN FETCH a.advertAttrs aa " + "WHERE a.advertType IN :advertType AND a.status IN :status "
				+ "ORDER BY a.advertPriorityType DESC, "
				+ "a.createdUpdated.createdTimestamp DESC, a.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findAdvertsWithStoryNameAdvertType", query = "SELECT a FROM Advert a "
				+ "INNER JOIN FETCH a.advertStoryNames asn "
				+ "WHERE a.advertType IN :advertType AND a.status IN :status "
				+ "AND asn.storyName IN :storyName AND asn.status IN :advertStoryNameStatus "
				+ "ORDER BY a.advertPriorityType DESC, "
				+ "a.createdUpdated.createdTimestamp DESC, a.createdUpdated.updatedTimestamp DESC"),
		@NamedQuery(name = "findWithStatus", query = "SELECT a FROM Advert a WHERE a.status IN :status "
				+ "ORDER BY a.advertPriorityType, "
				+ "a.createdUpdated.createdTimestamp DESC, a.createdUpdated.updatedTimestamp DESC")})
public class Advert extends AbstractAppEntity {

	private static final long serialVersionUID = -6313439384160751918L;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "advert_type")
	private AdvertType advertType;

	@Column(name = "advert_priority_type")
	private AdvertPriorityType advertPriorityType;

	@Column(name = "status")
	private AdvertStatus status;

	@Column(name = "start_timestamp")
	private OffsetDateTime startTimestamp;

	@Column(name = "end_timestamp")
	private OffsetDateTime endTimestamp;

	@OneToMany(mappedBy = "advert", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AdvertAttr> advertAttrs;

	@OneToMany(mappedBy = "advert")
	private List<AdvertStoryName> advertStoryNames;

	public Advert() {
		super();

		setStatus(AdvertStatus.NEW);
	}

	public void addAdvertAttr(AdvertAttr advertAttr) {
		getAdvertAttrs().add(advertAttr);
		advertAttr.setAdvert(this);
	}

	public void addAdvertStoryName(AdvertStoryName advertStoryName) {
		getAdvertStoryNames().add(advertStoryName);
		advertStoryName.setAdvert(this);
	}

	public List<AdvertAttr> getAdvertAttrs() {
		return advertAttrs;
	}

	public void setAdvertAttrs(List<AdvertAttr> advertAttrs) {
		this.advertAttrs = advertAttrs;
	}

	public AdvertPriorityType getAdvertPriorityType() {
		return advertPriorityType;
	}

	public void setAdvertPriorityType(AdvertPriorityType advertPriorityType) {
		this.advertPriorityType = advertPriorityType;
	}

	public List<AdvertStoryName> getAdvertStoryNames() {
		return advertStoryNames;
	}

	public void setAdvertStoryNames(List<AdvertStoryName> advertStoryNames) {
		this.advertStoryNames = advertStoryNames;
	}

	public AdvertType getAdvertType() {
		return advertType;
	}

	public void setAdvertType(AdvertType advertType) {
		this.advertType = advertType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OffsetDateTime getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(OffsetDateTime endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OffsetDateTime getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(OffsetDateTime startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public AdvertStatus getStatus() {
		return status;
	}

	public void setStatus(AdvertStatus status) {
		this.status = status;
	}

	public String getAttrValue(AdvertAttrType advertAttrType) {
		Optional<AdvertAttr> aav = advertAttrs.stream().filter(v -> v.getAdvertAttrType() == advertAttrType)
				.findFirst();
		return aav.isPresent() ? aav.get().getValue() : "";
	}

	@Override
	public int hashCode() {
		return Objects.hash(advertPriorityType, advertType, description, name, status);
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
		Advert other = (Advert) obj;
		return advertPriorityType == other.advertPriorityType && advertType == other.advertType
				&& Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& status == other.status;
	}

	public boolean removeAdvertAttr(AdvertAttr advertAttr) {
		advertAttr.setAdvert(null);
		return getAdvertAttrs().remove(advertAttr);
	}

	@Override
	public String toString() {
		return "Advert{" + "name='" + name + '\'' + ", advertType=" + advertType + ", advertPriorityType="
				+ advertPriorityType + ", status=" + status + ", startTimestamp=" + startTimestamp + ", endTimestamp="
				+ endTimestamp + "} " + super.toString();
	}
}
