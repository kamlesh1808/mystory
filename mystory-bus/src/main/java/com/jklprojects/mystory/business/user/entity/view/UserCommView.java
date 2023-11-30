/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.business.api.entity.view.AbstractAppView;
import com.jklprojects.mystory.business.user.entity.UserComm;
import com.jklprojects.mystory.business.user.entity.UserCommAttr;
import com.jklprojects.mystory.common.ex.AppCodeException;
import com.jklprojects.mystory.common.user.UserCommName;
import com.jklprojects.mystory.common.user.UserCommStatus;
import com.jklprojects.mystory.common.util.CollUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2016-01-23
 */
public class UserCommView extends AbstractAppView<UserComm> {
	private static final long serialVersionUID = -9147111832562732733L;

	private static final XLogger logger = XLoggerFactory.getXLogger(UserCommView.class);

	private UserCommName userCommName;
	private UserCommStatus status;
	private Long userId;
	private Long userOAuthId;
	private List<UserCommAttrView> attrs = new ArrayList<>();

	public UserCommView() {
		super(UserComm.class);
	}

	public void addUserCommAttr(UserCommAttrView userCommAttr) {
		logger.entry(userCommAttr);
		if (!attrs.contains(userCommAttr)) {
			logger.trace("addUserCommAttr {}", userCommAttr.toString());
			attrs.add(userCommAttr);
		}
	}

	public List<UserCommAttrView> getAttrs() {
		return attrs;
	}

	public UserCommStatus getStatus() {
		return status;
	}

	public Long getUserId() {
		return userId;
	}

	public UserCommName getUserCommName() {
		return userCommName;
	}

	public Long getUserOAuthId() {
		return userOAuthId;
	}

	public void setAttrs(List<UserCommAttrView> attrs) {
		this.attrs = attrs;
	}

	@Override
	public UserComm setEntityFromView(UserComm entity, boolean setBi) throws AppCodeException {
		entity.setStatus(getStatus());
		entity.setUserCommName(getUserCommName());

		if (setBi) {
			if (CollUtil.isNotEmpty(getAttrs())) {
				for (UserCommAttrView attr : getAttrs()) {
					entity.addAttr(attr.setEntityFromView(new UserCommAttr(), false));
				}
			} else {
				entity.setAttrs(Collections.emptyList());
			}
		}

		return entity;
	}

	public void setStatus(UserCommStatus status) {
		this.status = status;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserCommName(UserCommName userCommName) {
		this.userCommName = userCommName;
	}

	public void setUserOAuthId(Long userOAuthId) {
		this.userOAuthId = userOAuthId;
	}

	@Override
	public UserCommView setViewFromEntity(UserComm entity, boolean setBi) {
		super.setViewFromEntity(entity, setBi);

		setUserCommName(entity.getUserCommName());
		setStatus(entity.getStatus());

		for (UserCommAttr at : entity.getAttrs()) {
			addUserCommAttr(new UserCommAttrView().setViewFromEntity(at, false));
		}

		if (setBi) {
			if (entity.getUserId() != null) {
				setUserId(entity.getUserId());
			}

			if (entity.getUserOAuthId() != null) {
				setUserOAuthId(entity.getUserOAuthId());
			}
		}

		return this;
	}
}
