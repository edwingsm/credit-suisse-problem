package com.credit.suisse.assignment.domain;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.credit.suisse.assignment.domain.core.EventCache;
import com.credit.suisse.assignment.domain.core.CacheListener;
import com.credit.suisse.assignment.domain.model.Event;
import com.credit.suisse.assignment.domain.model.EventState;

public class BaseEventCache extends AbrstractConcurrentEventCache  {
	private static final Logger logger = LogManager.getLogger(BaseEventCache.class);
	
	private EventState eventState;
	private CacheListener notifier;
	
	public CacheListener getNotifier() {
		return notifier;
	}

	public void setNotifier(CacheListener notifier) {
		this.notifier = notifier;
	}

	public BaseEventCache(EventState eventState,int threshold,CacheListener notifier) {
		this.eventState = eventState;
		this.cacheThreshold=threshold;
		this.notifier=notifier;
	}

	public EventState getEventState() {
		return eventState;
	}

	public void setEventState(EventState eventState) {
		this.eventState = eventState;
	}

	@Override
	public void saveEvent(Event eventModel) {
		logger.debug("Saving  {}",eventModel.getId());
		cacheMap.put(eventModel.getId(), eventModel);
		if(getCacheSize()>=cacheThreshold) {
			notifier.notify(eventState);
		}
	}

	@Override
	public void deleteEvent(String eventId) {
		logger.debug("Deleting  Event {}", eventId);
		cacheMap.remove(eventId);
		
	}

	@Override
	public int getCacheSize() {
		return !cacheMap.isEmpty()?cacheMap.size():0;
	}

	@Override
	public Event getEvent(String eventId) {
		logger.debug("Get Event from  Event cache ",eventId);
		return cacheMap.get(eventId);
	}
	
	@Override
	public void clearCache() {
		cacheMap.clear();
	}

	@Override
	public Map<String, Event> getCache() {
		return cacheMap;
	}

	@Override
	public void refresh() {
		logger.debug("Refreshing Cache");
		notifier.notify(this.eventState);
	}

}
