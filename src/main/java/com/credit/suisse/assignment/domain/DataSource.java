package com.credit.suisse.assignment.domain;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

	private static final Logger logger = LogManager.getLogger(DataSource.class);
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
		config.setJdbcUrl("jdbc:hsqldb:mem:event");
		config.setUsername("sa");
		config.setMaximumPoolSize(5);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
		ds = new HikariDataSource(config);
	}

	private DataSource() {
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection= ds.getConnection();
		} catch (SQLException e) {
			logger.error("Couldn't conenct to DB , so processing couldn't be happen, \n Exitign app");
			System.exit(0);
		}
		return connection;
	}
}
