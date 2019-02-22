===================
Shop Offer Rest API
===================

This REST API is built using Spring Boot and uses JPA and H2 in memory database.

==================
Notes:
1. There is some configuration for the H2 database in the application.properties file.
2. There is some sample data to populate the database in the data.sql file.
3. The application was built using TDD, there are tests for the Controller, 
	service layer, repository, JPA, and the Offer entity.
4. The Controller is mapped with the uri: /shop
5. The Offer entity has fields for Id, name, description, currency, price, isActive and expiryDate.
6. The expiryDate is in milliseconds. If the current date and time is after the expiryDate then the offer will be considered expired.
	Calls to expired offers will throw an OfferExpiredException which will return a message in a response object indicating it has expired.
	Milliseconds were used here just for simplification.
7. The isActive field is used to indicate that an offer is currently active. The offer can be cancelled by setting this flag to false.
8. To view an offer you can use the uriL /shop/offers/{Id} which performs a Get request. 
9. If an offer is not found then an OfferNotFound Exception will be thrown and a Response should be returned indicating that it was not found.
10. To create a new offer use the uri mapping /shop/offers/save and post some sample JSON such as
	{
		"offerName" : "New Offer",
		"description" : "This is a new offer.",
		"price" : "5.00",
		"currency" : "USD",
		"expiryDate" : "1551273873222",
		"isActive" : "true"
		}
11. To cancel an offer you can use the uri: /shop/offers/cancel/{Id}. This will use a put request to set the isActive flag to false for the offer. 
	subsequent calls to get this offer should throw and OfferCancelledException which will return a message in a response object indicating that
	the offer is no longer available.
