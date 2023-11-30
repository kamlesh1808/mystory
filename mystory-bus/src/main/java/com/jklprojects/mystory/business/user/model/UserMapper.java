/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.model;

import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.business.user.service.rs.rto.UsersRTO;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2019-06-23
 */
public class UserMapper implements IUserMapper {

	private static IUserMapper INSTANCE;

	/** no-arg constructor */
	private UserMapper() {
		super();
	}

	/** @return */
	public static final IUserMapper getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new UserMapper();
		}
		return INSTANCE;
	}

	@Override
	public UsersRTO createUsersRTO(final User source) {
		return new UsersRTO(new UserView().setViewFromEntity(source, true), null);
	}

	@Override
	public void updateUser(final UsersRTO source, User dest) {
		dest.setId(source.getId());
		dest.setFirstName(source.getFirstName());
		dest.setLastName(source.getLastName());
		dest.setEmail(source.getEmail());
		dest.setEmail(source.getEmail());
		dest.setStatus(source.getStatus());
		dest.setUserRoleType(source.getUserRoleType());

		try {
			long uid = Long.parseLong(source.getUserUID());
			dest.setUid(uid);
		} catch (NumberFormatException nfe) {
			//
		}
	}
}
