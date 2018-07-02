package com.credit.suisse.assignment.adapter;

import com.credit.suisse.assignment.AppComponents;
import com.credit.suisse.assignment.domain.core.AnalyzerListner;

public class AnalyzerListnerImpl implements AnalyzerListner {

	@Override
	public void finished(String eventId) {
		AppComponents.getExecutorService().execute(() -> AppComponents.getStartEventCache().deleteEvent(eventId));
		AppComponents.getExecutorService().execute(() -> AppComponents.getFinishedEventCache().deleteEvent(eventId));
	}
	
}
