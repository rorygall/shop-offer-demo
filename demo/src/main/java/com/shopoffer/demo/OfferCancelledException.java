package com.shopoffer.demo;

public class OfferCancelledException extends RuntimeException {


	public OfferCancelledException(String message, Throwable cause) {
		super(message, cause);
	}

	public OfferCancelledException(String message) {
		super(message);
	}

	public OfferCancelledException(Throwable cause) {
		super(cause);
	}


}
