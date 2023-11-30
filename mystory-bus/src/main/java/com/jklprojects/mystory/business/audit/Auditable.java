/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.audit;

import com.jklprojects.mystory.business.audit.entity.AuditEntity;
import com.jklprojects.mystory.business.audit.entity.AuditEventType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Oct 6, 2015
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.FIELD})
public @interface Auditable {
	AuditEventType[] auditEventType() default AuditEventType.CREATE;

	/**
	 * Audit (log) value. Default is true.
	 *
	 * @return
	 */
	boolean auditValue() default true;

	/**
	 * name of the auditable entity.
	 *
	 * @return
	 */
	AuditEntity name() default AuditEntity.NONE;
}
