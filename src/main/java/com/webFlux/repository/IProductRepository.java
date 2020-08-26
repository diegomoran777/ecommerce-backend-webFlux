package com.webFlux.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.webFlux.models.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Interface of product repository that connect with database 
 * @author Diego Moran
 * @version: 1.0
 */
public interface IProductRepository extends ReactiveMongoRepository<Product, String> {
	/**
	 * Returns list of product by categoryId
	 * @return List
	 * @param id
	 */
	Flux<Product> findByCategoryId(String id);
	
	/**
	 * Returns a product by categoryId and productId
	 * @return Product
	 * @param categoryId
	 * @param id
	 */
	Mono<Product> findByCategoryIdAndId(String categoryId, String id);
	
	/**
	 * Returns a product by productId
	 * @return Product
	 * @param id
	 */
	Mono<Product> findById(String id);
	
	/**
	 * Delete all product with specific categoryId
	 * @return Long
	 * @param id
	 */
	Mono<Long> deleteByCategoryId(String id);
	
	
	/**
	 * Search products by specific parameters
	 * @return List
	 * @param name
	 * @param numberExtern
	 * @param type
	 * @param categoryId
	 */
	@Query("{ 'name' : { $regex: ?0, $options: 'i'}, 'numberExtern' : { $regex: ?1, $options: 'i'}, 'type' : { $regex: ?2, $options: 'i'}, 'categoryId' : { $regex: ?3, $options: 'i'} }")
	Flux<Product> searchByThreeParams(String name, String numberExtern, String type, String categoryId);
	
	/**
	 * Update categoryName from all products by categoryName
	 * @param categoryNameNew
	 * @param categoryNameOld
	 */
	@Query("{'categoryName': ?0},{ $set: {'categoryName': ?1}, , false, true")
    void setCatecoryNameNew(String categoryNameNew, String categoryNameOld);
}
