package com.shopoffer.demo;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.shopoffer.demo.dao.OfferRepository;
import com.shopoffer.demo.entity.Offer;
import com.shopoffer.demo.service.OfferService;

/**
 * <p>This class tests the service layer which contains the business logic for the application.</p>
 * 
 * <p>We will use a mock of the repository in our calls to the service methods</p>
 * 
 * @author Rory
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {

	// Mock the repository 
	@Mock 
	private OfferRepository offerRepository;
	
	// Service layer object
	private OfferService offerService;
	
	@Before()
	public void setup()
	{
		// Inject the mock repository into the service object
		offerService = new OfferService(offerRepository);
	}
	
	/**
	 * <p>This method should test whether we can retrieve an offer from the service layer</p>
	 */
	@Test
	public void getOfferDetails_returnsOfferInfo() {
		DateTime nowPlusOneHour = new DateTime().plusHours(1);

		// Create a mock offer to use for calls to the service object
		Offer offerMock = new Offer((Long)1L, 
				"sale", 
				"£50 cash back on orders over £500.",
				50.00,
				"GBP",
				nowPlusOneHour.getMillis(),
				true);
		
		// Return the mock for any call to getOfferByName on service object
		BDDMockito.given(offerService.getOfferById(ArgumentMatchers.anyLong()))
			.willReturn(Optional.of(offerMock));
		
		// Call the service layer
		Offer offer = offerService.getOfferById((Long)1L).orElse(new Offer());
		
		// Perform tests
		Assert.assertEquals(offer.getId(), (Long) 1L);
		Assertions.assertThat(offer.getId()).isEqualTo((Long)1L);
		Assertions.assertThat(offer.getOfferName()).isNotBlank();
		Assertions.assertThat(offer.getOfferName()).isEqualTo("sale");
		Assertions.assertThat(offer.getDescription()).isNotBlank();
		Assertions.assertThat(offer.getDescription()).isEqualTo("£50 cash back on orders over £500.");
		Assertions.assertThat(offer.getCurrency()).isNotBlank();
		Assertions.assertThat(offer.getCurrency()).isEqualTo("GBP");
		Assertions.assertThat(offer.getPrice()).isNotNull();
		Assertions.assertThat(offer.getPrice()).isEqualTo(50.00);
		Assertions.assertThat(offer.getCurrency()).isEqualTo("GBP");
		DateTime now = new DateTime();
		DateTime offerExpiry = new DateTime(offer.getExpiryDate());
		Assertions.assertThat(offer.getExpiryDate()).isNotNull();
		Assertions.assertThat(offerExpiry).isGreaterThan(now);	
		Assertions.assertThat(offer.getIsActive()).isNotNull();
		Assertions.assertThat(offer.getIsActive()).isEqualTo(true);
	
	}
	
	/**
	 * <p>This method should test if we get an OfferNotFoundException thrown
	 * from the service object when we request an offer that doesn't exist.</p> 
	 */
	@Test(expected=OfferNotFoundException.class)
	public void getOfferDetails_offerNotFound()
	{
		BDDMockito.given(offerService.getOfferById((long)1L)).willThrow(new OfferNotFoundException());
		
		offerService.getOfferDetails((long)1L);
	}
	
	/**
	 * <p>This method should test whether we get an OfferExpiredException thrown
	 * from the service object when we request an offer that has an expiration date
	 * in the past.</p>
	 */
	@Test(expected=OfferExpiredException.class)
	public void getOfferDetails_offerExpired()
	{
		// Set the expiration date to an hour in the past
		DateTime nowMinusOneHour = new DateTime().minusHours(1);

		Offer offerMock = new Offer((Long)1L, 
				"sale", 
				"£50 cash back on orders over £500.",
				50.00,
				"GBP",
				nowMinusOneHour.getMillis(),
				true);

		// The mock offer returned should be in the past
		BDDMockito.given(offerService.getOfferById(ArgumentMatchers.anyLong()))
		.willReturn(Optional.of(offerMock));
		
		// This call should try to get an offer with an expiration date that has passed
		// hence it should throw an OfferExpiredException
		offerService.getOfferDetails((Long)1L);
	}
	
	/**
	 * <p>This method should test whether we get an OfferCancelledException thrown
	 * from the service object when we request an offer that has been cancelled.</p>
	 */
	@Test(expected=OfferCancelledException.class)
	public void getOfferDetails_offerCancelled()
	{
		// The expiry is in the future so this should be ok
		DateTime nowPlusOneHour = new DateTime().plusHours(1);

		Offer offerMock = new Offer((Long)1L, 
				"sale", 
				"£50 cash back on orders over £500.",
				50.00,
				"GBP",
				nowPlusOneHour.getMillis(),
				false);

		// The mock offer returned should have an isActive flag set to false
		BDDMockito.given(offerService.getOfferById(ArgumentMatchers.anyLong()))
		.willReturn(Optional.of(offerMock));
		
		// This call should try to get an offer which has been cancelled
		// Hence it should throw an OfferCancelledException
		offerService.getOfferDetails((long)1L);
		
	}

	/**
	 * <p>This method should test canceling an offer via the service layer</p>
	 */
	@Test
	public void cancelOffer_shouldBeCancelled()
	{
		// Expiry date in future
		DateTime nowPlusOneHour = new DateTime().plusHours(1);

		final Long offerId = (Long)1L;
		
		Offer offerMock = new Offer((Long)1L, 
				"sale", 
				"£50 cash back on orders over £500.",
				50.00,
				"GBP",
				nowPlusOneHour.getMillis(),
				true);
		
		// Mock the offer to be returned to have flag set to active
		BDDMockito.given(offerService.getOfferById(ArgumentMatchers.anyLong()))
			.willReturn(Optional.of(offerMock));
		
		// Check that we can get the offer from the service layer and that its is currently active
		Offer offerBeforeCancel = offerService.getOfferById(offerId).orElse(new Offer());
		Assertions.assertThat(offerBeforeCancel.getIsActive()).isNotNull();
		Assertions.assertThat(offerBeforeCancel.getIsActive()).isEqualTo(true);
		
		// Try cancelling the offer via service object call
		try {
			offerService.cancelOffer(offerId);
		} catch (Exception e)
		{
			Assertions.assertThat(e instanceof OfferCancelledException);
		}
		
		// Get the offer again and check that it is now no longer active
		Offer offerAfterExpiry = offerService.getOfferById(offerId).orElse(new Offer());

		Assertions.assertThat(offerAfterExpiry.getIsActive()).isNotNull();
		Assertions.assertThat(offerAfterExpiry.getIsActive()).isEqualTo(false);
	}
}
