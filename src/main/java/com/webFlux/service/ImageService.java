package com.webFlux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webFlux.models.Image;
import com.webFlux.repository.IImageRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Class that manages the functions of the repositories 
 * @author Diego Moran
 * @version: 1.0
 */
@Service
@Transactional
public class ImageService implements IImageService {
	
	@Autowired
	IImageRepository repo;

	/**
	 * Returns all types images
	 * @return Set of types
	 */
	@Override
	public Flux<String> getAllTypesFromImage() {
		return repo.findAll().map(image -> image.getType()).distinct(); 
	}

	/**
	 * Returns specific types images
	 * @return Set of types
	 * @param categoryId
	 */
	@Override
	public Flux<String> getTypesImageByCategory(String categoryId) {
		return repo.findByCategoryId(categoryId)
				.map(image -> image.getType()).distinct();
	}

	/**
	 * Returns a boolean value if can delete a image by id
	 * @return ResponseEntity
	 * @param id
	 */
	@Override
	public Mono<ResponseEntity<Boolean>> deleteImageById(String id) {
		return	repo.deleteById(id)
				.map(p -> ResponseEntity.ok().body(true))
				.doOnError(p -> ResponseEntity.ok().body(false));
	}

	/**
	 * Returns a boolean value if can delete all image by category id
	 * @return ResponseEntity
	 * @param id
	 */
	@Override
	public Mono<Boolean> deleteImagesFromCategory(String id) {
		try {
			repo.deleteByCategoryId(id);
			return Mono.just(true);
		} catch (Exception e) {
			System.out.println("Fallo borrar imagenes de una categoria");
			return Mono.just(false);
		}
	}
	
	/**
	 * Returns all images
	 * @return List of images
	 */
	@Override
	public Flux<Image> findAllImages() {
		return repo.findAll();
	}

	/**
	 * Returns a image by id
	 * @return Image
	 * @param id
	 */
	@Override
	public Mono<Image> findImageById(String id) {
		return repo.findById(id);
	}

	/**
	 * Returns a list of images by categoryId
	 * @return Image list
	 * @param id
	 */
	@Override
	public Flux<Image> findImagesByCategoryId(String id) {
		return repo.findByCategoryId(id);
	}

	/**
	 * Returns a image by categoryId and imageId
	 * @return Image
	 * @param categoryId
	 * @param id
	 */
	@Override
	public Mono<Image> findImagesByCategoryAndImageId(String categoryId, String id) {
		return repo.findByCategoryIdAndId(categoryId, id);
	}

	/**
	 * Returns a image which will be deleted
	 * @return Image
	 * @param image
	 */
	@Override
	public Mono<Image> saveUpdateImage(Image image) {
		return repo.save(image);
	}

	/**
	 * Returns a image list by parameters
	 * @return Image list
	 * @param name
	 * @param type
	 * @param categoryId
	 */
	@Override
	public Flux<Image> getImageByParamsId(String name, String type, String categoryId) {
		return repo.searchByThreeParams(name, type, categoryId);
	}
	
	/**
	 * Update categoryName from all images by categoryName
	 * @param categoryNameNew
	 * @param categoryNameOld
	 */
	@Override
	public Mono<ResponseEntity<?>> updateCategoryNameNewImage(String categoryNameNew, String categoryNameOld) {
		try {
			repo.setCatecoryNameNew(categoryNameNew, categoryNameOld);
			return Mono.just(ResponseEntity.ok().body(true));
		} catch (Exception e) {
			return Mono.just(ResponseEntity.ok().body(false));
		}
	}

}
