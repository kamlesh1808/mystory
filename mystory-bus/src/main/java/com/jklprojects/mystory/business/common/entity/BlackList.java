/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.common.entity.converter.BlackListTypeAttrConverter;
import com.jklprojects.mystory.business.common.entity.converter.RecordStatusConverter;
import com.jklprojects.mystory.common.RecordStatus;
import com.jklprojects.mystory.common.blacklist.BlackListType;
import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * BlackList entity.
 *
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 2.18.06.23
 */
@Entity
@Table(name = "A_BLACK_LIST")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
		@NamedQuery(name = "findBlackListAll", query = "SELECT bl FROM BlackList bl ORDER by bl.blackListType ASC"),
		@NamedQuery(name = "findBlackListWithBlackListType", query = "SELECT bl FROM BlackList bl WHERE bl.blackListType = :blackListType")})
public class BlackList extends AbstractAppEntity implements Comparable<BlackList> {

	private static final long serialVersionUID = -7285777085535633961L;

	@Column(name = "black_list_type_id")
	@Convert(converter = BlackListTypeAttrConverter.class)
	private BlackListType blackListType;

	@Column(name = "black_list_value")
	private String blackListValue;

	@Column(name = "record_status")
	@Convert(converter = RecordStatusConverter.class)
	private RecordStatus recordStatus;

	/** public no-arg constructor */
	public BlackList() {
		super();

		recordStatus = RecordStatus.ACTIVE;
	}

	/**
	 * Compare using name
	 *
	 * @param o
	 * @return
	 * @see String#compareTo(String)
	 */
	@Override
	public int compareTo(BlackList o) {
		return Comparator.comparing(BlackList::getBlackListType).thenComparing(BlackList::getBlackListValue)
				.compare(this, o);
	}

	public BlackListType getBlackListType() {
		return blackListType;
	}

	public String getBlackListValue() {
		return blackListValue;
	}

	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		com.jklprojects.mystory.business.common.entity.BlackList blackList = (com.jklprojects.mystory.business.common.entity.BlackList) o;

		if (getBlackListType() != blackList.getBlackListType())
			return false;
		return getBlackListValue() != null
				? getBlackListValue().equals(blackList.getBlackListValue())
				: blackList.getBlackListValue() == null;
	}

	@Override
	public int hashCode() {
		int result = getBlackListType() != null ? getBlackListType().hashCode() : 0;
		result = 31 * result + (getBlackListValue() != null ? getBlackListValue().hashCode() : 0);
		return result;
	}

	public void setBlackListType(BlackListType blackListType) {
		this.blackListType = blackListType;
	}

	public void setBlackListValue(String blackListValue) {
		this.blackListValue = blackListValue;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Override
	public String toString() {
		return "BlackList{" + "blackListType=" + blackListType + ", blackListValue='" + blackListValue + '\''
				+ ", recordStatus=" + recordStatus + "} " + super.toString();
	}
}
