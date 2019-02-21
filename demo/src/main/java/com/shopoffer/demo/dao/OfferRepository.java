package com.shopoffer.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.shopoffer.demo.entity.Offer;

@Component
public interface OfferRepository extends CrudRepository<Offer, Long>{

	//public Collection<Offer> findByOfferName(String name);


}
