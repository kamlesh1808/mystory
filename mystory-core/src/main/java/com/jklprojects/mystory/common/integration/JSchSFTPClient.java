/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.common.integration;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.InputStream;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * Responsible for providing sFTP capabilities using either as
 * {@link CredentialType#USER_NAME_PASSWORD} or {@link CredentialType#SSH_KEY}.
 *
 * <p>
 * It is using <a href="http://www.jcraft.com/jsch/">JCraft jsch</a>, a pure
 * Java implementation of SSH2.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, May 4, 2016
 */
public class JSchSFTPClient implements FileTransferer {
	private static final XLogger logger = XLoggerFactory.getXLogger(JSchSFTPClient.class);

	private final String host;
	private final int port;
	private final CredentialType credentialType;

	private final String userName;
	private final String password;

	private final String privateKey;
	private final String keyPassPhrase;

	private Session session = null;
	private ChannelSftp sftpChannel = null;

	/**
	 * @param host
	 * @param port
	 * @param credentialType
	 * @param userName
	 * @param password
	 * @param privateKey
	 * @param keyPassPhrase
	 */
	public JSchSFTPClient(final String hostName, final int port, final CredentialType credentialType,
			final String userName, final String password, final String privateKeyFile, final String keyPassPhrase) {

		this.host = hostName;
		this.port = port;
		this.credentialType = credentialType;

		this.userName = userName;
		this.password = password;

		this.keyPassPhrase = keyPassPhrase;
		this.privateKey = privateKeyFile;
	}

	@Override
	public boolean checkDirectoryExists(String directory) {
		try {
			sftpChannel.ls(directory);
			return true;
		} catch (SftpException e) {
			return false;
		}
	}

	@Override
	public boolean connect() throws SystemIntegrationEx {
		boolean connected = false;

		logger.debug(String.format("credentialType: %s, userName: %s, host: %s, port: %d", credentialType, userName,
				host, port));

		JSch jSch = new JSch();

		try {
			if (CredentialType.SSH_KEY == credentialType) {
				jSch.addIdentity(privateKey, keyPassPhrase);
				logger.debug(String.format("privateKey: %s", privateKey));
			}

			session = jSch.getSession(userName, host, port);

			session.setConfig("StrictHostKeyChecking", "no");
			if (CredentialType.USER_NAME_PASSWORD == credentialType) {
				session.setPassword(password);
			}

			session.connect();
			Channel channel = session.openChannel("sftp");
			if (channel != null) {
				sftpChannel = (ChannelSftp) channel;
				sftpChannel.connect();

				logger.info(String.format("Connected to host: %s", host));
			} else {
				throw new SystemIntegrationEx("Unable to open sftp Channel");
			}

			connected = true;
		} catch (JSchException e) {
			throw new SystemIntegrationEx(
					String.format("Error connecting to host: %s credential type: %s", host, credentialType), e);
		}

		return connected;
	}

	@Override
	public boolean disconnect() {
		if (sftpChannel != null) {
			sftpChannel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
		if (sftpChannel != null) {
			return sftpChannel.isClosed();
		} else {
			return true;
		}
	}

	@Override
	public void put(String srcFile, String dest) throws SystemIntegrationEx {
		try {
			sftpChannel.put(srcFile, dest);
		} catch (SftpException e) {
			throw new SystemIntegrationEx(String.format("Error transfering file: %s to destination: %s", srcFile, dest),
					e);
		}
	}

	@Override
	public void put(InputStream is, String dest) throws SystemIntegrationEx {
		try {
			sftpChannel.put(is, dest);
		} catch (SftpException e) {
			throw new SystemIntegrationEx(String.format("Error transfering input stream to destination: %s", dest), e);
		}
	}
}
