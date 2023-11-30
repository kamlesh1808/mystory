/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service.api;

import com.jklprojects.mystory.business.user.entity.UserComm;
import com.jklprojects.mystory.common.user.UserCommStatus;
import java.util.List;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 1, 2015-10-30
 */
public interface UserCommService {

	UserComm createUserComm(UserComm userComm);

	List<UserComm> findUserComms(UserCommStatus... status);

	void sendUserComms();
}
