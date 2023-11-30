/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.service.api;

import com.jklprojects.mystory.business.user.entity.User;
import java.util.List;
import javax.ejb.Remote;

/**
 * EJB Remote interface for User.
 *
 * @author Kamleshkumar Patel
 * @version 1, 2015-APR-04
 */
@Remote
public interface UserRemote {

	List<User> findEntityAll();
}
