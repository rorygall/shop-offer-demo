package com.shopoffer.demo;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shopoffer.demo.dao.OfferRepository;
import com.shopoffer.demo.entity.Offer;

/**
 * <p>This that will simply test that our  finder method works</p>
 * @author Rory
 *
 */
@DataJpaTest
@RunWith(SpringRunner.class)
public class OfferRepositoryTest {

	@Autowired
	private OfferRepository offerRepository;
	
	@Test
	public void findByOfferName()
	{
		DateTime nowPlusOneHour = new DateTime().plusHours(1);
		Offer offer = new Offer((Long)1L, 
				"sale", 
				"£50 cash back on orders over £500.",
				50.00,
				"GBP",
				nowPlusOneHour.getMillis(),
				true);

		offerRepository.save(offer);
		
		Optional<Offer> byOfferId = offerRepository.findById(1L);
		Assertions.assertThat(byOfferId.get().getId()).isGreaterThan(0L);
		Assertions.assertThat(byOfferId.get().getOfferName()).isEqualTo("sale");

	}
}
