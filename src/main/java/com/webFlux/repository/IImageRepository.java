package com.webFlux.repository;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.webFlux.models.Image;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Interface of product repository that connect with database 
 * @author Diego Moran
 * @version: 1.0
 */
public interface IImageRepository extends ReactiveMongoRepository<Image, String> {

	/**
	 * Returns list of images by categoryId
	 * @return List
	 * @param id
	 */
	Flux<Image> findByCategoryId(String id);
	
	/**
	 * Returns a image by categoryId and imageId
	 * @return Image
	 * @param categoryId
	 * @param id
	 */
	Mono<Image> findByCategoryIdAndId(String categoryId, String id);
	
	/**
	 * Returns a image by imageId
	 * @return Image
	 * @param id
	 */
	Mono<Image> findById(String id);
	
	/**
	 * Delete all image with specific categoryId
	 * @return Long
	 * @param id
	 */
	Long deleteByCategoryId(String id);
	
	/**
	 * Search images by specific parameters
	 * @return List
	 * @param name
	 * @param type
	 * @param categoryId
	 */
	@Query("{ 'name' : { $regex: ?0, $options: 'i'}, 'type' : { $regex: ?1, $options: 'i'}, 'categoryId' : { $regex: ?2, $options: 'i'} }")
	Flux<Image> searchByThreeParams(String name, String type, String categoryId);
	
	/**
	 * Update categoryName from all images by categoryName
	 * @param categoryNameNew
	 * @param categoryNameOld
	 */
	@Query("{'categoryName': ?0},{ $set: {'categoryName': ?1}, , false, true")
    void setCatecoryNameNew(String categoryNameNew, String categoryNameOld);
}
