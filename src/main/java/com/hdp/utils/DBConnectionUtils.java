package com.hdp.utils;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DBConnectionUtils {

	private static final Logger logger = LogManager.getLogger(DBConnectionUtils.class);

	public static Properties dbProperties;

	// File for database and connection pooling configurations
	public static final String configFileName = "config/dbConfig.properties";

	public static final String driverClassName = "driverClassName";
	public static final String jdbcUrl = "jdbcUrl";
	public static final String username = "username";
	public static final String password = "password";

	public static final String initialPoolSize = "initialPoolSize";
	public static final String minPoolSize = "minPoolSize";
	public static final String acquireIncrement = "acquireIncrement";
	public static final String maxPoolSize = "maxPoolSize";
	public static final String maxStatements = "maxStatements";

	public static ComboPooledDataSource datasource;

	public static void load(final String path) throws IOException, PropertyVetoException {
		// load dbConfig.properties
		logger.info("Loading dbConfig.properties file started");
		dbProperties = CommonUtils.loadFileToProperties(Paths.get(path, configFileName).toString());
		logger.info("Loading dbConfig.properties file completed");

		// create pool of db connection
		logger.info("Creating connection pool started");
		connectionPooling();
		logger.info("Creating connection pool completed");
	}

	public static void connectionPooling() throws PropertyVetoException {

		datasource = new ComboPooledDataSource();

		// datasource configurations
		datasource.setDriverClass(dbProperties.getProperty(driverClassName));
		datasource.setJdbcUrl(dbProperties.getProperty(jdbcUrl));
		datasource.setUser(dbProperties.getProperty(username));
		datasource.setPassword(dbProperties.getProperty(password));

		// connection pool configurations
		datasource.setInitialPoolSize(Integer.parseInt(dbProperties.get(initialPoolSize).toString()));
		datasource.setMinPoolSize(Integer.parseInt(dbProperties.get(minPoolSize).toString()));
		datasource.setAcquireIncrement(Integer.parseInt(dbProperties.get(acquireIncrement).toString()));
		datasource.setMaxPoolSize(Integer.parseInt(dbProperties.get(maxPoolSize).toString()));
		datasource.setMaxStatements(Integer.parseInt(dbProperties.get(maxStatements).toString()));
	}

	public static Connection connection() throws SQLException {
		// pulling single connection from pool
		return datasource.getConnection();
	}

	public static ComboPooledDataSource datasource() {
		return datasource;
	}
}
