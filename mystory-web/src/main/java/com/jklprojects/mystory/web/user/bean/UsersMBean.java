/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.web.user.bean;

import com.jklprojects.mystory.business.user.entity.view.UserOAuth2View;
import com.jklprojects.mystory.business.user.entity.view.UserView;
import com.jklprojects.mystory.web.api.beans.AbstractAppManagedBean;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 4, 2020-05-23
 * @since 4, 2020-05-23
 */
@Named("usersMBean")
@RequestScoped
public class UsersMBean extends AbstractAppManagedBean {

	private static final XLogger logger = XLoggerFactory.getXLogger(UsersMBean.class);

	private List<UserView> users = new ArrayList<>();
	private List<UserOAuth2View> usersOAuth2 = new ArrayList<>();

	@PostConstruct
	public void init() {
		logger.entry();

		users = getUserService().findUserAllOrderByIdDesc();
		usersOAuth2 = getUserOAuth2Service().findUserOAuth2AllOrderByIdDesc();
	}

	public List<UserView> getUsers() {
		return users;
	}

	public List<UserOAuth2View> getUsersOAuth2() {
		return usersOAuth2;
	}
}
