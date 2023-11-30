/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.user.entity.view;

import com.jklprojects.mystory.business.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2018-04-23
 */
@DisplayName("User View Test")
class UserViewTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test _ to Views Sorted")
	void test_toViewsSorted() {
		List<User> users = new ArrayList<>();
		User userA = new User();
		userA.setUserName("amathews34");
		userA.setFirstName("Ashton");
		userA.setLastName("Mathews");
		users.add(userA);
		User userB = new User();
		userB.setUserName("mmarner16");
		userB.setFirstName("Mitch");
		userB.setLastName("Marner");
		users.add(userB);
		User userC = new User();
		userC.setUserName("aburrows23");
		userC.setFirstName("Alex");
		userC.setLastName("Burrows");
		users.add(userC);
		User userD = new User();
		userD.setUserName("tbozak42");
		userD.setFirstName("Tyler");
		userD.setLastName("Bozak");
		users.add(userD);
		User userE = new User();
		userE.setUserName("zhyman11");
		userE.setFirstName("Zach");
		userE.setLastName("Hyman");
		users.add(userE);
		List<UserView> userViews = UserView.toViewsSorted(users);
		for (UserView userView : userViews) {
			System.out.print(userView.toString());
		}
		UserView userView = null;
		for (int i = 0; i < userViews.size(); i++) {
			userView = userViews.get(i);
			if (i == 0) {
				Assertions.assertTrue(userView.getUserName().equals("aburrows23"));
			}
			if (i == 1) {
				Assertions.assertTrue(userView.getUserName().equals("amathews34"));
			}
			if (i == 2) {
				Assertions.assertTrue(userView.getUserName().equals("mmarner16"));
			}
			if (i == 3) {
				Assertions.assertTrue(userView.getUserName().equals("tbozak42"));
			}
			if (i == 4) {
				Assertions.assertTrue(userView.getUserName().equals("zhyman11"));
			}
		}
	}
}
