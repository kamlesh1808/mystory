/** Copyright (c) 2015 - 2022 JKLProjects Inc. All Rights Reserved. */
package com.jklprojects.mystory.business.common.datasource;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * @author Kamleshkumar N. Patel
 * @version 3, 2018-12-23
 * @see https://github.com/arquillian/arquillian-extension-persistence/blob/master/int-tests/src/test/java/org/jboss/arquillian/integration/persistence/datasource/postgresql/PostgreSqlDataSource.java
 */
@DataSourceDefinition(name = "java:jboss/jdbc/MyStoryDS", className = "org.postgresql.ds.PGSimpleDataSource", url = "jdbc:postgresql://localhost:5432/mystorydb", databaseName = "mystorydb", user = "mystoryadmin", password = "adminadmin")
@Singleton
@Startup
public class PostgreSqlDataSource {
}
