package com.credit.suisse.assignment.adapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.credit.suisse.assignment.domain.core.EventRepository;
import com.credit.suisse.assignment.domain.model.Event;

public class EventRepositoryImpl implements EventRepository {

	private Connection connection;
	private final String insertquery="INSERT INTO events (id,type,host,duration,alert) VALUES (?,?,?,?,?)";
	private static final Logger logger = LogManager.getLogger(EventRepositoryImpl.class);
	public EventRepositoryImpl(Connection connection) {
		this.connection = connection;
		try {
			initDatabase();
		} catch (SQLException e) {
			logger.error("DB not created");
			e.printStackTrace();
		}
	}

	@Override
	public void saveCompletedEvent(Event model, int duration) throws SQLException {
		logger.info("Saving Event {}",model.getId());
		boolean alert = duration > 4 ? true : false;
		PreparedStatement preparedStatement= connection.prepareStatement(insertquery);
		preparedStatement.setString(1, model.getId());
		preparedStatement.setString(2, model.getType());
		preparedStatement.setString(3, model.getHost());
		preparedStatement.setInt(4, duration);
		preparedStatement.setBoolean(5, alert);
		preparedStatement.execute();
	}

	private void initDatabase() throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute("CREATE TABLE events (id VARCHAR(25) NOT NULL, type VARCHAR(20) ,"
				+ "host VARCHAR(20) , duration INT NOT NULL, alert BOOLEAN DEFAULT FALSE NOT NULL, PRIMARY KEY (id))");
		connection.commit();

	}

	@Override
	public void findAllCompletedEvent() throws SQLException{
		PreparedStatement stmt=connection.prepareStatement("select * from events");  
		ResultSet rs=stmt.executeQuery();  
		while(rs.next()){  
		System.out.println(rs.getString(1));  
		}  
		
	}

}
