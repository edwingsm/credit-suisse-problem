package com.credit.suisse.assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.credit.suisse.assignment.domain.model.Event;
import com.credit.suisse.assignment.domain.model.EventState;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventLogFileReader implements Runnable {
	private static final Logger logger = LogManager.getLogger(EventLogFileReader.class);
	private String pathToFile;
		
	public EventLogFileReader(String pathToFile) {
		this.pathToFile=pathToFile;
	}


	public void startReading() {
		AppComponents.getIsFileReading().set(true);
		try (Stream<String> input = Files.lines(Paths.get(pathToFile))) {
			logger.debug("Reading file line by line");
			input.map((lines -> convert(lines, Event.class))).filter(Objects::nonNull).forEach(e -> {
				if (EventState.STARTED.equals(e.getState())) {
					AppComponents.getStartEventCache().saveEvent(e);
				} else if (EventState.FINISHED.equals(e.getState())) {
					AppComponents.getFinishedEventCache().saveEvent(e);	
				}
			});
		}catch (IOException e) {
			logger.error("Couldn't read the file {}, exiting app",pathToFile);
			System.exit(0);
		}
		
	}
	
	public void postReading() {
		logger.debug("Executing Post File reading actions");
		AppComponents.getIsFileReading().set(false);
	}

	private  <T> T convert(String string, Class<T> pojo) {
		logger.debug("Converting string to event object {}",string);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(string, pojo);
		} catch (IOException e) {
			logger.error("Couldn't convert line {} in file to event Object",string);
		}
		//This not a good approach to return null, but thought it's handy in this case
		return null;
	}
	
	@Override
	public void run() {
		logger.debug("Running File reader on thread");
		startReading();
	}


	public static void main(String[] args) throws Exception{
		if(Objects.isNull(args) || args.length<=0 || args[0].trim().length()==0) {
			logger.info("Invalid file name, exiting app");
			System.exit(0);
		}
		
		ExecutorService executor = AppComponents.getExecutorService();
		EventLogFileReader logFileReader = new EventLogFileReader(args[0]);
		executor.execute(logFileReader);
		
	}
}
