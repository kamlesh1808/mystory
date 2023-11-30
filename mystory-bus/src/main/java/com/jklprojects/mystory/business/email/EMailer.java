/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.email;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.jklprojects.mystory.business.email.api.EMailStatus;
import com.jklprojects.mystory.business.email.api.MimeType;
import com.jklprojects.mystory.common.ex.AppException;
import com.jklprojects.mystory.common.util.CollUtil;
import com.jklprojects.mystory.common.util.StringUtil;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLogger.Level;
import org.slf4j.ext.XLoggerFactory;

/**
 * EMailer is a worker class that provides e-mail related functionalities.
 *
 * <p>
 * http://www.oracle.com/technetwork/java/javamail/faq/index.html
 *
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-18
 * @version 1, 2016-12-16
 * @version 1, 2017-11-23
 * @version 2, 2018-04-23
 * @version 4, 2020-11-23
 */
public class EMailer {
	private static final ExecutorService execServ = Executors.newFixedThreadPool(10);
	private static final XLogger logger = XLoggerFactory.getXLogger(EMailer.class);
	private Session session;
	private Map<String, String> httpHeaders = new HashMap<>();

	/**
	 * @param userName
	 * @param password
	 * @param smtpProps
	 * @param httpHeaders
	 */
	public EMailer(String userName, String password, Properties smtpProps, Map<String, String> httpHeaders) {
		session = Session.getInstance(smtpProps, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		this.httpHeaders = httpHeaders;
	}

	/**
	 * Send email Asynchronously without expecting a result.
	 *
	 * @param message
	 */
	public void sendEMailAsync(Message message) {

		Runnable run = () -> {
			try {
				send(message);
			} catch (AppException e) {
				RuntimeException t = new RuntimeException(e);

				logger.throwing(t);

				throw t;
			}
		};

		new Thread(run).start();
	}

	/**
	 * Send email Asynchronously, wait for completion and return result.
	 *
	 * @param message
	 * @return result email status
	 */
	public EMailStatus sendEMailASyncESResult(Message message) {
		EMailStatus result = EMailStatus.INIT;

		FutureTask<EMailStatus> ft = new FutureTask<EMailStatus>(() -> {
			EMailStatus es = EMailStatus.INIT;

			try {
				send(message);

				es = EMailStatus.SENT;
			} catch (AppException e) {
				RuntimeException t = new RuntimeException(e);

				logger.throwing(t);

				throw t;
			}
			return es;
		});

		execServ.execute(ft);

		while (true) {
			try {
				if (ft.isDone()) {
					result = ft.get();
					break;
				} else {
					// wait and then try to retrieve the result
					result = ft.get(100, TimeUnit.MILLISECONDS);
					break;
				}
			} catch (InterruptedException | ExecutionException e) {
				logger.catching(Level.TRACE, e);
			} catch (TimeoutException e) {
				logger.catching(Level.TRACE, e);
			}
		}

		return result;
	}

	/**
	 * Send message.
	 *
	 * @throws AppException
	 *             if an error is encountered sending email
	 */
	private void send(Message message) throws AppException {
		try {
			logger.info("Sending email: subject: {}, recipients: {} ", message.getSubject(),
					message.getAllRecipients());

			Transport.send(message);
		} catch (MessagingException e) {
			throw new AppException(e.getMessage(), e);
		}
	}

	/**
	 * Prepare Message with given parameters if mail app is enabled. If the mime
	 * type is empty, default "text/plain" is used.
	 *
	 * @param from
	 * @param to
	 * @param subject
	 * @param emailContent
	 * @param attachFiles
	 * @return message
	 * @throws AppException
	 *             if the session is null or if parameter validation fails, of if an
	 *             error sending email
	 */
	public Message prepare(String from, List<String> to, String subject, String emailContent, List<File> attachFiles)
			throws AppException {

		try {
			if (session == null) {
				throw EMailerExceptions.EMAILER_007.newAppCodeException();
			}

			// parameter validation
			if (StringUtil.isEmpty(from)) {
				throw EMailerExceptions.EMAILER_002.newAppCodeException();
			}

			if (StringUtil.isEmpty(subject)) {
				throw EMailerExceptions.EMAILER_003.newAppCodeException();
			}

			if (CollUtil.isEmpty(to)) {
				throw EMailerExceptions.EMAILER_004.newAppCodeException();
			}

			if (StringUtil.isEmpty(emailContent)) {
				throw EMailerExceptions.EMAILER_006.newAppCodeException();
			}

			Message message = new MimeMessage(session);
			if (StringUtil.isNotEmpty(emailContent)) {
				message.setContent(emailContent, MimeType.TEXT_HTML.getValue());
			}

			message.setFrom(new InternetAddress(from));

			if (CollUtil.isNotEmpty(to)) {
				to.stream().forEach(t -> {
					try {
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(t));
					} catch (MessagingException e) {
						logger.error(e.getMessage(), e);
					}
				});
			}

			message.setSubject(subject);

			// add body parts - attachments
			if (attachFiles != null && !attachFiles.isEmpty()) {

				Multipart multipart = new MimeMultipart();

				// creates body part for the message
				for (File file : attachFiles) {
					MimeBodyPart attachPart = new MimeBodyPart();
					attachPart.attachFile(file);
					multipart.addBodyPart(attachPart);
				}

				// sets the multi-part as message's content
				message.setContent(multipart);

				if (CollUtil.isNotEmpty(httpHeaders)) {
					httpHeaders.forEach((headerName, headerValue) -> {
						try {
							message.addHeader(headerName, headerValue);
						} catch (MessagingException e) {
						}
					});
				}
			}

			return message;
		} catch (MessagingException e) {
			throw new AppException(e.getMessage(), e);
		} catch (IOException e) {
			throw new AppException(e.getMessage(), e);
		}
	}

}
