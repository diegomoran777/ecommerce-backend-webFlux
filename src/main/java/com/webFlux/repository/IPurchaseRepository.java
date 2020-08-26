package com.webFlux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.webFlux.models.Purchase;

/**
 * Interface of purchase repository that connect with database 
 * @author Diego Moran
 * @version: 1.0
 */
public interface IPurchaseRepository extends ReactiveMongoRepository<Purchase, String> {
	
}
