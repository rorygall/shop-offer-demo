package com.shopoffer.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopoffer.demo.OfferCancelledException;
import com.shopoffer.demo.OfferErrorResponse;
import com.shopoffer.demo.OfferExpiredException;
import com.shopoffer.demo.OfferNotFoundException;
import com.shopoffer.demo.entity.Offer;
import com.shopoffer.demo.service.OfferService;

/**
 * <p>This class represents the Controller for the REST API</p>
 * <p>It contains uri mappings for
 * 		<ul>
 * 			<li>/offers/{name} - for retrieving an offer</li>
 * 			<li>/offers/save - for creating a new offer</li>
 * 			<li>/offers/cancel/{name} - for cancelling and offer</li>
 * 		</ul> 
 * </p>
 * @author Rory
 *
 */
@RestController
@RequestMapping("/shop")
@Component
public class OfferRestController {

	// Service layer object
	private OfferService offerService;
	
	public OfferRestController() {}	
	@Autowired
	public OfferRestController(OfferService offerService)
	{
		this.offerService = offerService;
	}

	/**
	 * <p>GetMapping to retrieve an offer from the repository via the service layer</p>
	 * 
	 * @param name
	 * 				The name of the offer to retrieve
	 * @return
	 */
	@GetMapping("/offers/{Id}")
	private Offer getOffer(@PathVariable Long Id)
	{
		Offer offer = offerService.getOfferDetails(Id);
		return offer;
	}
	
	/**
	 * <p>PostMapping to create a new Offer and save it to the repository via the service layer</p>
	 * 
	 * @param offer
	 * 			The offer to create
	 * @return
	 */
	@PostMapping("/offers/save")
	private Offer createOffer(@RequestBody Offer offer)
	{
		return offerService.saveOffer(offer);
	}
	
	/**
	 * <p>Put Mapping to allow canceling an order via a call to the service layer.</p>
	 * 
	 * @param name
	 * 			The name of the offer to cancel
	 */
	@PutMapping("/offers/cancel/{Id}")
	private void cancelOffer(@PathVariable Long Id)
	{
		offerService.cancelOffer(Id);
	}
	

	/** 
	 * <p>Exception Handler to return a ResponseEntity to the caller for an OfferNotFoundException </p>
	 * <p>This will contain basic error information which can be passed back to the caller.</p>
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<OfferErrorResponse> handleException(OfferNotFoundException ex)
	{
		OfferErrorResponse error = new OfferErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);		
	}

	/** 
	 * <p>Exception Handler to return a ResponseEntity to the caller for Offer Expired Exceptions </p>
	 * <p>This will contain basic error information which can be passed back to the caller.</p>
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<OfferErrorResponse> handleException(OfferExpiredException ex)
	{
		OfferErrorResponse error = new OfferErrorResponse();
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);		
	}

	/** 
	 * <p>Exception Handler to return a ResponseEntity to the caller for an OfferCancelledException </p>
	 * <p>This will contain basic error information which can be passed back to the caller.</p>
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<OfferErrorResponse> handleException(OfferCancelledException ex)
	{
		OfferErrorResponse error = new OfferErrorResponse();
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);		
	}

	/** 
	 * <p>Exception Handler to return a ResponseEntity to the caller for General Exceptions </p>
	 * <p>This will contain basic error information which can be passed back to the caller.</p>
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<OfferErrorResponse> handleException(Exception ex)
	{
		OfferErrorResponse error = new OfferErrorResponse();
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setTimestamp(System.currentTimeMillis());
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);		
	}

	
}
