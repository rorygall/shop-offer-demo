package com.shopoffer.demo;

/**
 * This is a class used to wrap a response to be sent in case of an Exception thrown from the service
 * @author Rory Gallagher
 *
 */
public class OfferErrorResponse {

	private int status;
	private String message;
	private long timestamp;

	public OfferErrorResponse() {
	}
	
	public OfferErrorResponse(int status, String message, long timestamp) {
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
