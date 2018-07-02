package com.credit.suisse.assignment.domain.core;

import com.credit.suisse.assignment.domain.model.Event;

public interface EventCache  {
	
	void saveEvent(Event eventModel);
	void deleteEvent(String eventId);
	Event getEvent(String eventId);
	int getCacheSize();
	void clearCache();
	
}
