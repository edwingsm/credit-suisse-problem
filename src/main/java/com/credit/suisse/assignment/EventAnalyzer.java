package com.credit.suisse.assignment;

import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.credit.suisse.assignment.domain.BaseEventCache;
import com.credit.suisse.assignment.domain.core.AnalyzerListner;
import com.credit.suisse.assignment.domain.core.CacheListener;
import com.credit.suisse.assignment.domain.core.EventRepository;
import com.credit.suisse.assignment.domain.model.Event;
import com.credit.suisse.assignment.domain.model.EventState;

public class EventAnalyzer implements Runnable {
	private static final Logger logger = LogManager.getLogger(EventAnalyzer.class);
	private EventRepository eventRepository=AppComponents.getEventrepository();
	private BaseEventCache basecache;
	private BaseEventCache compare;
	private EventState baseCacheEventState;
	private AnalyzerListner analyzerListner;

	public EventAnalyzer(EventState state,AnalyzerListner analyzerListner ) {
		this.analyzerListner=analyzerListner;
		this.baseCacheEventState=state;
		prepare();
	}

	private void prepare() {
		if(EventState.STARTED.equals(this.baseCacheEventState)) {
			this.basecache= AppComponents.getStartEventCache();
			this.compare=AppComponents.getFinishedEventCache();
			
		}else {
			this.basecache= AppComponents.getFinishedEventCache();
			this.compare=AppComponents.getStartEventCache();
		}
		
	}

	@Override
	public void run() {
		new Thread(()->AppComponents.getIsAnalyserRunning().set(true) ).start();
		anlayse();
		new Thread(()->AppComponents.getIsAnalyserRunning().set(false) ).start();

	}

	private void anlayse() {
	
		basecache.getCache().entrySet().parallelStream().forEach(e -> {
			Optional<Event> mappedEvent = Optional.ofNullable(compare.getEvent(e.getKey()));
			if (mappedEvent.isPresent()) {
			int durations = EventState.STARTED.equals(basecache.getEventState())
					? calculateDuration(mappedEvent.get(), e.getValue())
					: calculateDuration(e.getValue(), mappedEvent.get());
					logger.info("Duration {}", durations);
						try {
							eventRepository.saveCompletedEvent(e.getValue(), durations);
						} catch (SQLException e1) {
							logger.error("failed to save "+e1); 
						}
						analyzerListner.finished(e.getKey());
			}			

		});
		
	}

	private int calculateDuration(Event start, Event end) {
		return (int) (end.getTimestamp() - start.getTimestamp());
	}

}
