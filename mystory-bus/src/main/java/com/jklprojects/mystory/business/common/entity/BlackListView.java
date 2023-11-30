/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.entity;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.common.RecordStatus;
import com.jklprojects.mystory.common.blacklist.BlackListType;
import com.jklprojects.mystory.common.ex.AppCodeException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * BlackList view.
 *
 * @author Kamleshkumar N. Patel
 * @version 2, 2018-06-23
 * @since 2.18.06.23
 */
public class BlackListView extends AbstractAppView<BlackList> implements Comparable<BlackListView> {

	private static final long serialVersionUID = -7144121483630280148L;

	private BlackListType blackListType;
	private String blackListValue;
	private RecordStatus recordStatus;

	public BlackListView() {
		super(BlackList.class);
	}

	public static List<BlackListView> toViews(List<BlackList> entities) {
		List<BlackListView> views = new ArrayList<>();
		for (BlackList e : entities) {
			views.add(new BlackListView().setViewFromEntity(e, true));
		}
		return views;
	}

	/**
	 * Compare using name
	 *
	 * @param o
	 * @return
	 * @see String#compareTo(String)
	 */
	@Override
	public int compareTo(BlackListView o) {
		return Comparator.comparing(BlackListView::getBlackListType).thenComparing(BlackListView::getBlackListValue)
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

	public void setBlackListType(BlackListType blackListType) {
		this.blackListType = blackListType;
	}

	public void setBlackListValue(String blackListValue) {
		this.blackListValue = blackListValue;
	}

	@Override
	public BlackList setEntityFromView(BlackList entity, boolean setBi) throws AppCodeException {
		entity.setBlackListType(getBlackListType());
		entity.setBlackListValue(getBlackListValue());
		entity.setRecordStatus(getRecordStatus());

		return entity;
	}

	public void setRecordStatus(RecordStatus recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Override
	public BlackListView setViewFromEntity(BlackList entity, boolean setBi) {
		super.setViewFromEntity(entity, false);

		setBlackListType(entity.getBlackListType());
		setBlackListValue(entity.getBlackListValue());
		setRecordStatus(entity.getRecordStatus());

		return this;
	}
}
