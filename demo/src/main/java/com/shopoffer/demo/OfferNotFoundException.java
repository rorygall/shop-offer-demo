package com.shopoffer.demo;

public class OfferNotFoundException extends RuntimeException {

	public OfferNotFoundException () {}
	
	public OfferNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public OfferNotFoundException(String arg0) {
		super(arg0);
	}

	public OfferNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
