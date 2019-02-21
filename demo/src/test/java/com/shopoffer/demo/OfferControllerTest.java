package com.shopoffer.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.shopoffer.demo.rest.OfferRestController;
import com.shopoffer.demo.service.OfferService;
/**
 * <p>We will perform some tests in this class to mock the behaviour of the Rest Controller
 * We will not be testing the underlying service or repository layers here.</p>
 * 
 * @author Rory
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OfferRestController.class)
public class OfferControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OfferService offerService;

	/**
	 * <p>This method should test that we can create a new object via a post to uri: /offers/save </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldSaveOffer() throws Exception {
		/*DateTime futureDate1Week =  new DateTime().plusWeeks(1);
		DateTime pastDate1Week =  new DateTime().minusWeeks(1);
		System.out.println("futureDate1Week : " + futureDate1Week.getMillis());
		System.out.println("pastDate1Week : " + pastDate1Week.getMillis());*/
		
		// Future date in milliseconds for expiration of offer
		DateTime futureDate =  new DateTime().plusHours(1);
		String timeInMillis = Long.toString( futureDate.getMillis() );
		
		// Mock a JSON body to post
		final String OFFER_REQUEST = "{\"Id\" : \"1\", "
				+ "\"offerName\" : \"sale\", "
				+ "\"description\" : \"£50 cash back on orders over £500.\", "
				+ "\"price\" : \"50.00\", "
				+ "\"currency\" : \"GBP\", "
				+ "\"expiryDate\" : \""+timeInMillis+"\", "
				+ "\"isActive\" : \"true\" "
				+ "}";

		// mock the call to the controller to post the new object
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.post("/shop/offers/save").content(OFFER_REQUEST)
								.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}
	
	/**
	 * <p> This method should test the Get request to retrieve an offer for the uri: /offers/{name}.</p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void getOffer() throws Exception
	{
		
		// mock the call to retrieve an offer named sale
		mockMvc.perform(MockMvcRequestBuilders.get("/shop/offers/10").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	/**
	 * <p>This method should test the put mapping for canceling an offer with uri: /offers/cancel/{name}</p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void cancelOffer() throws Exception
	{		
		final String OFFER_REQUEST = "{\"Id\" : \"10\"}";
		
		// Mock a call to cancel the offer named sale
		this.mockMvc
				.perform(
						MockMvcRequestBuilders.put("/shop/offers/cancel/10").content(OFFER_REQUEST))
				.andExpect(status().isOk());
		
	}
	

	
}
