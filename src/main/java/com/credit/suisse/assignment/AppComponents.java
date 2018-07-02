package com.credit.suisse.assignment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import com.credit.suisse.assignment.adapter.CacheListnerImpl;
import com.credit.suisse.assignment.adapter.EventRepositoryImpl;
import com.credit.suisse.assignment.domain.BaseEventCache;
import com.credit.suisse.assignment.domain.DataSource;
import com.credit.suisse.assignment.domain.core.CacheListener;
import com.credit.suisse.assignment.domain.core.EventRepository;
import com.credit.suisse.assignment.domain.model.EventState;

public class AppComponents {
	
	private static final EventRepository EVENTREPOSITORY = new EventRepositoryImpl(DataSource.getConnection());
	private static final CacheListener NOTIFIER = new CacheListnerImpl();
	private static final BaseEventCache FINISHED_EVENT_CACHE = new BaseEventCache(EventState.FINISHED, 1, NOTIFIER);
	private static final BaseEventCache START_EVENT_CACHE = new BaseEventCache(EventState.STARTED, 1, NOTIFIER);
	private static AtomicBoolean IS_ANALYSE_RUNNING = new AtomicBoolean(false);
	private static AtomicBoolean IS_FILE_READING = new AtomicBoolean(true);

	private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);
	
	 private AppComponents() {
		    throw new IllegalStateException("AppComponents class");
	}

	public static AtomicBoolean getIsAnalyserRunning() {
		return IS_ANALYSE_RUNNING;
	}
	
	public static AtomicBoolean getIsFileReading() {
		return IS_FILE_READING;
	}

	public static EventRepository getEventrepository() {
		return EVENTREPOSITORY;
	}
	public static BaseEventCache getFinishedEventCache() {
		return FINISHED_EVENT_CACHE;
	}
	public static BaseEventCache getStartEventCache() {
		return START_EVENT_CACHE;
	}

	public static ExecutorService getExecutorService() {
		return EXECUTOR_SERVICE;
	}

	public static CacheListener getNotifier() {
		return NOTIFIER;
	}
	
}
