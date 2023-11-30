/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.api;

import com.jklprojects.mystory.business.audit.entity.Audit;
import java.time.OffsetDateTime;
import javax.ejb.Local;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Oct 6, 2015
 */
@Local
public interface AuditService {

	Audit createAudit(Audit entity);

	<T> T find(long entityId, Class<T> entityClass);

	OffsetDateTime findUserLastSeen(String userUID);
}
