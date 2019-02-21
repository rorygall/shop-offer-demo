package com.shopoffer.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Offer {

	@Id
	@GeneratedValue
	private Long id;
	
	private String offerName;
	
	private String description;
	
	private double price;
	
	private String currency;
	
	private Long expiryDate;
	
	private Boolean isActive;

	public Offer() {}
	
	public Offer(Long id, String offerName, String description, double price, 
							String currency, Long expiryDate, Boolean isActive) {
		this.id = id;
		this.offerName = offerName;
		this.description = description;
		this.price = price;
		this.currency = currency;
		this.expiryDate = expiryDate;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
	
}
