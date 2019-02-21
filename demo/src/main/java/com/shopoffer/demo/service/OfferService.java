package com.shopoffer.demo.service;

import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopoffer.demo.OfferCancelledException;
import com.shopoffer.demo.OfferExpiredException;
import com.shopoffer.demo.OfferNotFoundException;
import com.shopoffer.demo.dao.OfferRepository;
import com.shopoffer.demo.entity.Offer;

/**
 * <p>This class represents the service layer for the application</p>
 * <p>All business logic should be restricted to methods in this class</p>
 * @author Rory
 *
 */
@Component
public class OfferService {

	// The respository object to retrieve and persist data
	
	@Autowired
	private OfferRepository offerRepository;
	
	public OfferService() {}
	
	@Autowired
	public OfferService(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}
	
	/**
	 * <p>This method will save an offer via a call to the repository</p>
	 * 
	 * @param offer
	 * @return
	 */
	public Offer saveOffer(Offer offer) {
		return offerRepository.save(offer);

	}
	
	/**
	 * <p>This method will find an offer by name via a call to the repository</p>
	 * 
	 * @param name
	 * @return
	 */
	//public Collection<Offer> getOfferByName(String name) {
	//	return offerRepository.findByOfferName(name);
	//}

	public Optional<Offer> getOfferById(Long Id) {
		return offerRepository.findById(Id);
		
	}
	/**
	 * <p>This method will return an Offer if it passes all sanity checks</p>
	 * <p>We need to check the following before returning an offer
	 * 		<ul>
	 * 			<li>We get an offer back from the repository matching the name passed in.</li>
	 * 			<li>the offer has not passed its expiration date.</li>
	 * 			<li>the offer has not been canceled.</li>
	 * 		</ul>
	 * </p>
	 * @param name
	 * @return
	 */
	public Offer getOfferDetails(Long Id) {
		Offer offerById = getOfferById(Id).orElse(null);
		
		// If no offer returned then throw an appropriate exception
		if(offerById == null )
			throw new OfferNotFoundException("The offer was not found with ID: " + Id);
		
		// Perform the expiration sanity check
		DateTime offerExpiry = null;
		try {			
			offerExpiry = new DateTime(offerById.getExpiryDate());
		} catch (Exception e)
		{
			return null;
		}
		
		DateTime now = new DateTime();
		if(offerExpiry.isBefore(now))
		{
			throw new OfferExpiredException("This offer has expired.");
		}
		
		// Perform the cancelled sanity check
		if(!offerById.getIsActive())
			throw new OfferCancelledException("This offer is no longer available.");
		
		// If all sanity checks pass then return the offer
		return offerById;
	}

	/**
	 * <p>This method will cancel an offer via an update call to the repository</p>
	 * <p>If we can retrieve the offer we will set its active flag to false to indicate
	 * that it is now cancelled. Then we will persist the offer to the repository</p>
	 * @param name
	 */
	public void cancelOffer(Long Id) {
		// Get the offer
		Offer offer = getOfferById(Id).orElse(new Offer());
		if(offer != null) {
			// Update the active status and save
			//
			offer.setIsActive(false);
			saveOffer(offer);
		}
	}

	
}
