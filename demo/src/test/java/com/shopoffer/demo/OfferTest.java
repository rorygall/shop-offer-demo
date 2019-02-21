package com.shopoffer.demo;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

import com.shopoffer.demo.entity.Offer;

/**
 * <p>This class test the offer resource can be constructed</p>
 *  
 * @author Rory
 *
 */
public class OfferTest {

	@Test
	public void creation()
	{
		// Create a new Offer Object with an expiration date in the future
		DateTime nowPlusOneHour = new DateTime().plusHours(1);
		Offer offer = new Offer((Long)1L, 
				"sale", 
				"£50 cash back on orders over £500.",
				50.00,
				"GBP",
				nowPlusOneHour.getMillis(),
				true);
		
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
		Assertions.assertThat(offer.getExpiryDate()).isNotNull();
		DateTime offerExpiry = new DateTime(offer.getExpiryDate(), DateTimeZone.UTC);
		DateTime now = new DateTime();
		Assertions.assertThat(offerExpiry).isGreaterThan(now);	
		Assertions.assertThat(offer.getIsActive()).isNotNull();
		Assertions.assertThat(offer.getIsActive()).isEqualTo(true);

	}
	
}
