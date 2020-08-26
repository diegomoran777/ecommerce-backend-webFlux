package com.webFlux.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.webFlux.models.ProductByUserCart;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Interface of Cart repository that connect with database 
 * @author Diego Moran
 * @version: 1.0
 */
public interface IProductByUserCartRepository extends  ReactiveMongoRepository<ProductByUserCart, String> {
	
	/**
	 * Returns a ProductByUserCart list by userName
	 * @return List
	 * @param userName
	 */
	Flux<ProductByUserCart> findByUserName(String userName);
	
	/**
	 * Returns a ProductByUserCart list
	 * @return List
	 * @param userName
	 * @param productId
	 */
	List<ProductByUserCart> findByUserNameAndProductId(String userName, String productId);
	
	/**
	 * Delete all productCart by specfic params
	 * @return Long
	 * @param userName
	 * @param productId
	 */
	Mono<Long> deleteByProductIdAndUserName(String productId, String userName);
	
	/**
	 * Returns a ProductByUserCart by productId
	 * @return ProductByUserCart 
	 * @param id
	 */
	Mono<ProductByUserCart> findByProductId(String id);
	
	/**
	 * Delete all productCart with specific userName
	 * @return Long
	 * @param userName
	 */
	Mono<Long> deleteByUserName(String userName);
	
	/**
	 * Delete all productCart with specific producId
	 * @return Long
	 * @param productId
	 */
	Mono<Long> deleteByProductId(String productId);
	
	
	/**
	 * Update userName from all products by userName
	 * @param userNameNew
	 * @param userNameOld
	 */
	@Query("{'userName': ?0},{ $set: {'userName': ?1}, , false, true")
    void setUserNameNew(String userNameNew, String userNameOld);
}
