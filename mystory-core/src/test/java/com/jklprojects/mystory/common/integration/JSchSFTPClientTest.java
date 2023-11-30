/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 4, 2016
 */
@DisplayName("J Sch SFTP Client Test")
class JSchSFTPClientTest {

	private JSchSFTPClient jSchSFTPClient;

	private static final String hostName = "test.rebex.net";

	private static final int port = 22;

	private static final String userName = "demo";

	private static final String password = "password";

	private static final String keyPassPhrase = null;

	private static final String privateKeyFile = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Disabled
	@DisplayName("Test Connect User Name Password")
	void testConnectUserNamePassword() {
		jSchSFTPClient = new JSchSFTPClient(hostName, port, CredentialType.USER_NAME_PASSWORD, userName, password,
				privateKeyFile, keyPassPhrase);
		try {
			assertTrue(jSchSFTPClient.connect());
		} catch (SystemIntegrationEx e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.jklprojects.mystory.common.integration.JSchSFTPClient#disconnect()}
	 * .
	 */
	@Test
	@Disabled
	@DisplayName("Test Disconnect")
	void testDisconnect() {
		jSchSFTPClient = new JSchSFTPClient(hostName, port, CredentialType.USER_NAME_PASSWORD, userName, password,
				privateKeyFile, keyPassPhrase);
		try {
			assertTrue(jSchSFTPClient.connect());
			assertTrue(jSchSFTPClient.disconnect());
		} catch (SystemIntegrationEx e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	@Disabled
	@DisplayName("Test Put")
	void testPut() {
		jSchSFTPClient = new JSchSFTPClient(hostName, port, CredentialType.USER_NAME_PASSWORD, userName, password,
				privateKeyFile, keyPassPhrase);
		try {
			assertTrue(jSchSFTPClient.connect());
			// jSchSFTPClient.put(new ByteArrayInputStream("test
			// sftp".getBytes(StandardCharsets.UTF_8)), "test.txt");
			assertTrue(jSchSFTPClient.disconnect());
		} catch (SystemIntegrationEx e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
