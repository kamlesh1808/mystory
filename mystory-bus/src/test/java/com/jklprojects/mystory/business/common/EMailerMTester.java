/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common;

import java.util.Properties;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2015-04-18
 * @version 1, 2016-12-16
 * @version 1, 2017-01-23
 * @version 2, 2018-04-23
 * @version 3, 2018-11-23
 */
public class EMailerMTester {

	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Usage: EMailerMTest from to userName password");
			System.exit(1);
		}
		EMailerMTester eMailerTest = new EMailerMTester();
		final String from = args[0];
		final String to = args[1];
		final String userName = args[2];
		final String password = args[3];
		eMailerTest.sendEMailTest(from, to, userName, password);
	}

	private boolean sendEMailTest(String from, String to, String userName, String password) {
		boolean success = false;
		String host = "smtp.mail.yahoo.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		/*
		 * // Get the Session object. Session session = Session.getInstance(props, new
		 * javax.mail.Authenticator() {
		 *
		 * @Override protected PasswordAuthentication getPasswordAuthentication() {
		 * return new PasswordAuthentication(userName, password); } });
		 *
		 * try { // Create a default MimeMessage object. Message message = new
		 * MimeMessage(session);
		 *
		 * // Set From: header field of the header. message.setFrom(new
		 * InternetAddress(from));
		 *
		 * // Set To: header field of the header.
		 * message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		 *
		 * // Set Subject: header field
		 * message.setSubject("Testing Subject using gmail");
		 *
		 * // Now set the actual message
		 * message.setText("Hello, this is sample for to check send " +
		 * "email using JavaMailAPI ");
		 *
		 * // Send message Transport.send(message);
		 *
		 * System.out.println("Sent message successfully....");
		 *
		 * } catch (MessagingException e) { throw new RuntimeException(e); }
		 */
		return success;
	}
}
