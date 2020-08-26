package com.webFlux.controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webFlux.dto.BodyRequest;
import com.webFlux.models.Image;
import com.webFlux.service.ImageService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = {"*"})
public class ControllerRestImage {
	
	@Autowired
	ImageService service;
	
	/**
	 * Get all images
	 * @return {@link List}
	 */
	@GetMapping("/images")
	public Flux<Image> getImages(){
		return service.findAllImages();
	}
	
	/**
	 * Get image by id
	 * @param image
	 * @return {@link List}
	 */
	@PostMapping("/getimageid")
	public Mono<Image> getImageById(@RequestBody Image image){
		return service.findImageById(image.getId());
	}
	
	/**
	 * Get image by id
	 * @param image
	 * @return {@link Image}
	 */
	@PostMapping("/get-image")
	public Mono<Image> getImage(@RequestBody Image image){
		return service.findImageById(image.getId());
	}
	
	/**
	 * Delete image by id
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Boolean>> deleteImageById(@PathVariable String id){
		return service.deleteImageById(id);  
	}
	
	/**
	 * Get images by categoryId
	 * @param image
	 * @return {@link List}
	 */
	@PostMapping("/bycategoryid")
	public Flux<Image> getImagesByCategoryID(@RequestBody Image image){
		return service.findImagesByCategoryId(image.getId());
	}
	
	/**
	 * Get image by categoryId and imageId
	 * @param image
	 * @return {@link List}
	 */
	@PostMapping("/bycategory-imageid")
	public Mono<Image> getImagesByCategoryAndImageId(@RequestBody Image image){
		return service.findImagesByCategoryAndImageId(image.getCategoryId(), image.getId());
	}
	
	/**
	 * Save and update image
	 * @param image
	 * @return {@link ResponseEntity<image>}
	 */
	@PostMapping("/save-update")
	public Mono<ResponseEntity<Image>> saveUpdateImage(Image image){
		return service.saveUpdateImage(image)
				.flatMap(i -> Mono.just(ResponseEntity.ok().body(i)));
	}
	
	/**
	 * Get all types of image
	 * @return {@link Set}
	 */
	@GetMapping("/types")
	public Flux<String> getAllTypes(){
		return service.getAllTypesFromImage();
	}
	
	/**
	 * Get all types of imageId
	 * @param id
	 * @return {@link Set}
	 */
	@GetMapping("/types/{id}")
	public Flux<String> getTypesById(@PathVariable String id){
		return service.getTypesImageByCategory(id);
	}
	
	/**
	 * Get image by param and CategoryId
	 * @param {@link RequestBody}
	 * @return {@link List}
	 */
	@PostMapping("/search-by-paramsid")
	public Flux<Image> getImagesByParamsId(@RequestBody BodyRequest body) {
		return service.getImageByParamsId(body.getSearchByName(), body.getSearchByType(), body.getCategoryId());
	}
	
	@PostMapping("/update-new-categoryname")
	public Mono<ResponseEntity<?>> updateNewCategoryNameOnLists(@RequestBody Map<String, String> res) {
		return service.updateCategoryNameNewImage(res.get("categoryNameNew"), res.get("categoryNameOld"));
	}
}
