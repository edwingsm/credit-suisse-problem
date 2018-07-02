package com.credit.suisse.assignment.domain.model;

public class Event {

	private String id;
	private long timestamp;
	private String type;
	private String host;
	private EventState state;
	
	
	
	public Event(String id, long timestamp, String type, String host, EventState state) {
		this.id = id;
		this.timestamp = timestamp;
		this.type = type;
		this.host = host;
		this.state = state;
	}
	
	public Event() {
		
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public EventState getState() {
		return state;
	}
	public void setState(EventState state) {
		this.state = state;
	}
	
	

	
}
