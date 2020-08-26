package com.webFlux.service;

import org.springframework.http.ResponseEntity;

import com.webFlux.models.Image;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IImageService {

	public Flux<String> getAllTypesFromImage();
	
	public Flux<String> getTypesImageByCategory(String categoryId);
	
	public Mono<ResponseEntity<Boolean>> deleteImageById(String id);
	
	public Mono<Boolean> deleteImagesFromCategory(String id);
	
	public Flux<Image> findAllImages();
	
	public Mono<Image> findImageById(String id);
	
	public Flux<Image> findImagesByCategoryId(String id);
	
	public Mono<Image> findImagesByCategoryAndImageId(String categoryId,  String id);
	
	public Mono<Image> saveUpdateImage(Image image);
	
	public Flux<Image> getImageByParamsId(String name, String type, String id);
	
	public Mono<ResponseEntity<?>> updateCategoryNameNewImage(String categoroyNameNew, String categoroyNameOld);
	
}
