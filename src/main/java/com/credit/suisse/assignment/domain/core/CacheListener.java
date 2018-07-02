package com.credit.suisse.assignment.domain.core;

import com.credit.suisse.assignment.domain.model.EventState;

public interface CacheListener {
	
	
	void notify(EventState eventState);
	void notify(String eventId);
	void postAnalyser();
	//void invokeAnalyser(EventState eventState);

}
