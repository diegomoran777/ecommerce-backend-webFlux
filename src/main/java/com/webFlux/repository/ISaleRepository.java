package com.webFlux.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.webFlux.models.Sale;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Interface of Sale repository that connect with database 
 * @author Diego Moran
 * @version: 1.0
 */
public interface ISaleRepository extends ReactiveMongoRepository<Sale, String> {
	
	/**
	 * Returns a sale by id
	 * @return Sale
	 * @param id
	 */
	Mono<Sale> findById(String id);
	
	/**
	 * Returns a list sale by delivered
	 * @return Sale list
	 * @param delivered
	 */
	Flux<Sale> findByDelivered(boolean delivered);
	
	/**
	 * Search sales by specific parameters
	 * @return List
	 * @param date_approved
	 * @param userName
	 * @param userMail
	 */
	@Query("{ 'date_approved' : { $regex: ?0, $options: 'i'}, 'userName' : { $regex: ?1, $options: 'i'}, 'userMail' : { $regex: ?2, $options: 'i'} }")
	Flux<Sale> searchByParams(String date_approved, String userName, String userMail);
	
	/**
	 * Update userName from all sales by userName
	 * @param userNameNew
	 * @param userNameOld
	 */
	@Query("{'userName': ?0},{ $set: {'userName': ?1}, , false, true")
    void setUserNameNew(String userNameNew, String userNameOld);
}
