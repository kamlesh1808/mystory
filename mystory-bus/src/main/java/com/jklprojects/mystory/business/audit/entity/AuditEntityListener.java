/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit.entity;

import com.jklprojects.mystory.business.api.entity.AbstractAppEntity;
import com.jklprojects.mystory.business.api.entity.PersistenceUtil;
import com.jklprojects.mystory.business.audit.Auditable;
import com.jklprojects.mystory.business.audit.api.AuditService;
import com.jklprojects.mystory.business.common.ejb.AppConfigService;
import com.jklprojects.mystory.common.audit.AuditType;
import com.jklprojects.mystory.common.util.CollUtil;
import java.lang.reflect.Field;
import javax.ejb.EJB;
import javax.persistence.Column;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PreUpdate;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, 2016-11-06
 * @version 2, 2018-02-02
 */
public class AuditEntityListener {
	private static final XLogger logger = XLoggerFactory.getXLogger(AuditEntityListener.class);

	@EJB
	private AuditService auditService;

	@EJB
	private AppConfigService appConfigService;

	public AuditEntityListener() {
	}

	private Audit createAudit(Object obj, AuditEventType auditEventType) {
		Audit audit = null;
		if (appConfigService != null && appConfigService.isAppAuditEnabled() && obj instanceof AbstractAppEntity) {
			AbstractAppEntity aae = (AbstractAppEntity) obj;
			Auditable auditableAnnotation = aae.getClass().getAnnotation(Auditable.class);

			if (auditEventType.in(auditableAnnotation.auditEventType())) {
				audit = new Audit(aae, AuditType.ENTITY_CRUD, auditEventType);

				for (Field field : aae.getAuditableFields()) {
					try {
						field.setAccessible(true);
						logger.debug(field.toString());
						Object fieldValueObj = field.get(obj);

						if (fieldValueObj == null || fieldValueObj != null && !CollUtil.isCollectionOrMap(fieldValueObj)
								&& !PersistenceUtil.isEntityClass(fieldValueObj.getClass())) {
							Column col = field.getDeclaredAnnotation(Column.class);
							String attrName = col != null ? col.name() : field.getName();

							String fieldValueStr = fieldValueObj != null
									? fieldValueObj.toString().length() > Audit.AUDIT_VALUE_LENGTH
											? fieldValueObj.toString().substring(0, Audit.AUDIT_VALUE_LENGTH)
											: fieldValueObj.toString()
									: "";
							AuditDetail ad = new AuditDetail(attrName, fieldValueStr);
							audit.addAuditDetail(ad);
						}

					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
						logger.debug(field.toString());
						logger.catching(e);
					}
				}
				audit = auditService.createAudit(audit);
			}
		}

		return audit;
	}

	@PostPersist
	private final void postPersist(Object obj) {
		createAudit(obj, AuditEventType.CREATE);
	}

	@PostLoad
	private final void postLoad(Object obj) {
		createAudit(obj, AuditEventType.RETRIEVE);
	}

	@PreUpdate
	private final void preUpdate(Object obj) {
		createAudit(obj, AuditEventType.PRE_UPDATE);
	}

	@PostRemove
	private final void postRemove(Object obj) {
		createAudit(obj, AuditEventType.DELETE);
	}
}
