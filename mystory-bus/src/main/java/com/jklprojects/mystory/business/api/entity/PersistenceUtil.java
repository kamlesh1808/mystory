/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.api.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.jklprojects.mystory.business.audit.Auditable;

/**
 * @author Kamleshkumar N. Patel
 * @version 1, Oct 9, 2015
 */
public class PersistenceUtil {
	private PersistenceUtil() {
	}

	/**
	 *
	 * <pre>
	*  Reference: http://docs.oracle.com/javaee/7/tutorial/persistence-intro001.htm#BNBQA
	37.1.1 Requirements for Entity Classes

	An entity class must follow these requirements.

	The class must be annotated with the javax.persistence.Entity annotation.

	The class must have a public or protected, no-argument constructor. The class may have other constructors.

	The class must not be declared final. No methods or persistent instance variables must be declared final.

	If an entity instance is passed by value as a detached object, such as through a session bean's remote business interface, the class must implement the Serializable interface.

	Entities may extend both entity and non-entity classes, and non-entity classes may extend entity classes.

	Persistent instance variables must be declared private, protected, or package-private and can be accessed directly only by the entity class's methods. Clients must access the entity's state through accessor or business methods.
	 *
	 * </pre>
	 *
	 * @param clazz
	 * @return
	 */
	public static final boolean isPersistenceClass(Class<?> clazz) {
		if (!Modifier.isFinal(clazz.getModifiers()) && clazz.isAnnotationPresent(Entity.class)) {
			for (Constructor<?> constr : clazz.getConstructors()) {
				int constructorMod = constr.getModifiers();
				if (constr.getParameterCount() == 0
						&& (Modifier.isPublic(constructorMod) || Modifier.isProtected(constructorMod))) {
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * Return all auditable fields; that is the class is persistence class and the
	 * field is persistence and also auditable.
	 *
	 * @param clazz
	 * @return
	 *
	 * @see #isPersistenceClass(Class)
	 * @see #isAuditableClass(Class)
	 */
	public static final List<Field> getAuditableFields(Class<?> clazz) {
		List<Field> persistentFields = new ArrayList<>();

		if (isPersistenceClass(clazz) && isAuditableClass(clazz)) {
			for (Field field : clazz.getDeclaredFields()) {
				if (_isPersistenceField(field) && _isAuditableField(field)) {
					persistentFields.add(field);
				}
			}
		}

		return persistentFields;
	}

	/**
	 * Determine if the field and it's class is persistence.
	 *
	 * @param field
	 * @return
	 *
	 * @see #isPersistenceClass(Class)
	 */
	public static final boolean isPersistenceField(Field field) {
		if (isPersistenceClass(field.getType())) {
			return _isPersistenceField(field);
		}
		return false;
	}

	/**
	 * Determine if the field is persistence. Does not check if the Field's class is
	 * also persistence.
	 *
	 * @param field
	 * @return
	 */
	private static final boolean _isPersistenceField(Field field) {
		int fieldModifier = field.getModifiers();
		return !Modifier.isFinal(fieldModifier) && !Modifier.isStatic(fieldModifier)
				&& !Modifier.isTransient(fieldModifier) && !field.isAnnotationPresent(Transient.class);
	}

	/**
	 * Determine if the class is auditable.
	 *
	 * @param clazz
	 * @return
	 */
	public static final boolean isAuditableClass(Class<?> clazz) {
		return clazz.getAnnotation(Auditable.class) != null;
	}

	/**
	 * Determine if the field and it's class is auditable.
	 *
	 * @param field
	 * @return
	 *
	 * @see #isAuditableClass(Class)
	 */
	public static final boolean isAuditableField(Field field) {
		return isAuditableClass(field.getClass()) && _isAuditableField(field);
	}

	/**
	 * Determine if the field is auditable.
	 *
	 * @param field
	 * @return
	 */
	private static final boolean _isAuditableField(Field field) {
		Auditable auditableAnnotation = field.getAnnotation(Auditable.class);
		return auditableAnnotation == null || (auditableAnnotation != null && auditableAnnotation.auditValue());
	}

	/**
	 * Determine if the class is Persistent Entity class.
	 *
	 * @param clazz
	 * @return
	 *
	 * @see javax.persistence.Entity
	 */
	public static final boolean isEntityClass(Class<?> clazz) {
		return clazz.getAnnotation(Entity.class) != null;
	}

}
