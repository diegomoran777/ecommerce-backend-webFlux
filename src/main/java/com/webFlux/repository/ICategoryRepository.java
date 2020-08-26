package com.webFlux.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.webFlux.models.Category;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Interface of Category repository that connect with database 
 * @author Diego Moran
 * @version: 1.0
 */
public interface ICategoryRepository extends ReactiveMongoRepository<Category, String> {
	/**
	 * Returns a category by categoryId
	 * @return Category
	 * @param id
	 */
	Mono<Category> findById(String id);
	
	/**
	 * Returns list of categories by type
	 * @return List
	 * @param type
	 */
	Flux<Category> findByType(String type);
	
	/**
	 * Returns a supplier list by specific parameters
	 * @return List suppliers
	 * @param name
	 * @param numberExtern
	 */
	@Query( "{ 'name' : { $regex: ?0, $options: 'i'}, 'type' : { $regex: ?1, $options: 'i'} }") 
	Flux<Category> searchByParam(String name, String type);
	
}
