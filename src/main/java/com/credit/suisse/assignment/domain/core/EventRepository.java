package com.credit.suisse.assignment.domain.core;

import java.sql.SQLException;

import com.credit.suisse.assignment.domain.model.Event;

public interface EventRepository {
	void saveCompletedEvent(Event model, int duration) throws SQLException;
	void findAllCompletedEvent() throws SQLException;
}
