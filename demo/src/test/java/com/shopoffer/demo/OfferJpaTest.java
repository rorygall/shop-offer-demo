package com.shopoffer.demo;

import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.shopoffer.demo.entity.Offer;

/**
 * <p>This class will test the mapping of the JPA Entity when it interacts with the JPA Entity Manager</p>
 * @author Rory
 *
 */
@DataJpaTest
@RunWith(SpringRunner.class)
public class OfferJpaTest {

	@Autowired
	private TestEntityManager tem;
	
	@Test
	public void mapping() {
		// Prove that when we save new Offer we get back what we expect

		DateTime nowPlusOneHour = new DateTime().plusHours(1);
		Offer blackFridayOffer = tem.persistFlushFind(new Offer(null, 
				"flash sale", 
				"£30 cash back on orders over £300.",
				30.00,
				"GBP",
				nowPlusOneHour.getMillis(),
				true)
				);
		
		Assertions.assertThat(blackFridayOffer.getOfferName()).isEqualTo("flash sale");
		Assertions.assertThat(blackFridayOffer.getId()).isNotNull();
		Assertions.assertThat(blackFridayOffer.getId()).isGreaterThan((Long) 0L);
	}
	
}
