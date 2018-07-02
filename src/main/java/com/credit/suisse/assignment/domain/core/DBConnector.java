package com.credit.suisse.assignment.domain.core;

import java.sql.Connection;

public interface DBConnector {

	Connection getConnection();
	void closeConnection();
}
