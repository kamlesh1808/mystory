/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.service;

import com.jklprojects.mystory.business.api.entity.AbstractEntityDAO;
import com.jklprojects.mystory.business.api.entity.CreatedUpdatedOffsetDateTime_;
import com.jklprojects.mystory.business.audit.api.AuditService;
import com.jklprojects.mystory.business.audit.entity.Audit;
import com.jklprojects.mystory.business.audit.entity.Audit_;
import java.time.OffsetDateTime;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-11-06
 * @version 2, 2018-05-23
 */
@Stateless
public class AuditServiceImpl extends AbstractEntityDAO<Audit> implements AuditService {

	private static final XLogger logger = XLoggerFactory.getXLogger(AuditServiceImpl.class);

	public AuditServiceImpl() {
		super(Audit.class);
	}

	@Override
	public Audit createAudit(Audit entity) {
		Audit audit = create(entity);

		return audit;
	}

	@Override
	public OffsetDateTime findUserLastSeen(String userUID) {
		logger.entry(userUID);

		CriteriaBuilder builder = getEM().getCriteriaBuilder();
		CriteriaQuery<OffsetDateTime> criteria = builder.createQuery(OffsetDateTime.class);
		Root<Audit> auditRoot = criteria.from(Audit.class);
		criteria.select(builder
				.greatest(auditRoot.get(Audit_.createdUpdated).get(CreatedUpdatedOffsetDateTime_.createdTimestamp)));
		criteria.where(builder.equal(auditRoot.get(Audit_.userUID), userUID));
		OffsetDateTime lastSeen = getEM().createQuery(criteria).getSingleResult();

		return lastSeen;
	}
}
