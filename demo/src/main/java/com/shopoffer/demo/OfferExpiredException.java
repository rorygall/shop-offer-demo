package com.shopoffer.demo;

public class OfferExpiredException extends RuntimeException {

	public OfferExpiredException(String message, Throwable cause) {
		super(message, cause);
	}

	public OfferExpiredException(String message) {
		super(message);
	}

	public OfferExpiredException(Throwable cause) {
		super(cause);
	}

}
