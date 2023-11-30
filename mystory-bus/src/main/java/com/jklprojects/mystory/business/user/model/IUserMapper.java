/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.model;

import com.jklprojects.mystory.business.user.entity.User;
import com.jklprojects.mystory.business.user.service.rs.rto.UsersRTO;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2019-06-23
 */
public interface IUserMapper {

	/**
	 * Create a new object from source.
	 *
	 * @param source
	 * @return
	 */
	UsersRTO createUsersRTO(final User source);

	/**
	 * Update the destination using the source object.
	 *
	 * @param source
	 * @param dest
	 */
	void updateUser(final UsersRTO source, User dest);
}
