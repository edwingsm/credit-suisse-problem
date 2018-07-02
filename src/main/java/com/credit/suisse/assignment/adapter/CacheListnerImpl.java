package com.credit.suisse.assignment.adapter;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.credit.suisse.assignment.AppComponents;
import com.credit.suisse.assignment.EventAnalyzer;
import com.credit.suisse.assignment.domain.core.CacheListener;
import com.credit.suisse.assignment.domain.model.EventState;

public class CacheListnerImpl implements CacheListener {
	private static final Logger logger = LogManager.getLogger(CacheListnerImpl.class);
	private Executor executor = Executors.newFixedThreadPool(10);
	private boolean reRunEnable=false;
	private EventState lastNotifed;

	@Override
	public void notify(EventState eventState) {
		lastNotifed=eventState;
		logger.info("Cache state {}", eventState);
		if (!isAnalyserRunning()) {
			startAnalyzer(eventState);
		}else {
			prepareForRerun(eventState);
		}
	}
	
	private boolean isAnalyserRunning() {
		return AppComponents.getIsAnalyserRunning().get();
	}
	
	private void startAnalyzer(EventState eventState) {
		if(EventState.STARTED.equals(eventState)) {
			executor.execute(new EventAnalyzer(eventState, new AnalyzerListnerImpl()));
		}else {
			executor.execute(new EventAnalyzer(eventState, new AnalyzerListnerImpl()));
		}
	}
	
	private void prepareForRerun(EventState eventState) {
		lastNotifed=eventState;
		reRunEnable=true;
	}
	
	@Override
	public void notify(String eventId ) {
	}

	@Override
	public void postAnalyser() {
		if(reRunEnable) {
			reRunEnable=false;
			notify(lastNotifed);
		}
	}

	/*@Override
	public void invokeAnalyser(EventState eventState) {
		notify(eventState);
	}*/

}
