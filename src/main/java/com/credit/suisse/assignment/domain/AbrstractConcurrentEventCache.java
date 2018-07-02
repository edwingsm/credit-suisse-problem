package com.credit.suisse.assignment.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.credit.suisse.assignment.domain.core.EventCache;
import com.credit.suisse.assignment.domain.model.Event;

public abstract class AbrstractConcurrentEventCache implements EventCache {
	protected Map<String, Event> cacheMap = new ConcurrentHashMap<>(); 
	protected final int defualtCacheThreshold = 0;
	protected int cacheThreshold = defualtCacheThreshold;
	public abstract Map<String, Event> getCache();
	public abstract void refresh();
}
