/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.email.ejb;

import java.io.File;
import java.util.List;

import javax.ejb.Local;

import com.jklprojects.mystory.common.ex.AppException;

/**
 * EMailer Service.
 *
 * @author Kamleshkumar N. Patel
 * @version 1, Apr 18, 2015
 * @version 2, Apr 26, 2016
 * @version 3, 2016-DEC-15
 * @version 4, 2017-Oct-23
 */
@Local
public interface EMailerService {

	void sendEmail(List<String> to, String subject, String html, List<File> attachments) throws AppException;

}
