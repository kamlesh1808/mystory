/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.integration;

import java.io.InputStream;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, May 4, 2016
 */
public interface FileTransferer {

	boolean checkDirectoryExists(String directory);

	/**
	 * Connect to destination host.
	 *
	 * @throws SystemIntegrationEx
	 *             if unable to connect
	 * @return true if connection was successful, false otherwise.
	 */
	boolean connect() throws SystemIntegrationEx;

	/**
	 * Disconnect from destination host.
	 *
	 * @return true if disconnected, false otherwise.
	 */
	boolean disconnect();

	/**
	 * Transfer file to destination.
	 *
	 * @param file
	 *            source file path
	 * @param dest
	 *            destination file path
	 * @throws SystemIntegrationEx
	 */
	void put(String file, String dest) throws SystemIntegrationEx;

	void put(InputStream is, String dest) throws SystemIntegrationEx;
}
