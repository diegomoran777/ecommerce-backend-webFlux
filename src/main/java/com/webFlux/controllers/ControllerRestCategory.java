package com.webFlux.controllers;

import java.util.List;
import java.util.Map;

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

import com.webFlux.models.Category;
import com.webFlux.service.GeneralService;
import com.webFlux.service.ImageService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = {"*"})
public class ControllerRestCategory {
	
	
	@Autowired
	GeneralService service;
	
	@Autowired
	ImageService imageService; 
	
	/**
	 * Get all category
	 * @return {@link List}
	 */
	@GetMapping("/categories")
	public Flux<Category> getCategories(){
		return service.findAllCategories();
	}
	
	/**
	 * Get all category
	 * @return {@link List}
	 */
	@GetMapping("/categories-by-type/{type}")
	public Flux<Category> getCategoriesByType(@PathVariable String type){
		return service.findByType(type);
	}
	
	/**
	 * Get category by id
	 * @param supplier {@link RequestBody}}
	 * @return {@link List}
	 */
	@PostMapping("/getcategoryid")
	public Mono<Category> getCategoryById(@RequestBody Category category){
		return service.findCategoryById(category.getId());
	}
	
	/**
	 * Get category by id
	 * @param category {@link RequestBody}
	 * @return {@link category}
	 */
	@PostMapping("/get-category")
	public Mono<Category> getCategory(@RequestBody Category category){
		return service.findCategoryById(category.getId());
	}
	
	/**
	 * Delete category by id
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping("/category-products/{id}")
	public Mono<ResponseEntity<Void>> deleteCategoryProductsById(@PathVariable String id){
		return service.deleteProductsFromCategory(id).then(service.deleteCategoryById(id));
				 
	}
	
	/**
	 * Delete category by id
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping("/category-images/{id}")
	public Mono<ResponseEntity<Void>> deleteCategoryImagesById(@PathVariable String id){
		return imageService.deleteImagesFromCategory(id).then(service.deleteCategoryById(id)); 
	}
	
	/**
	 * Save or update category
	 * @param category
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/save-update")
	public Mono<ResponseEntity<Category>> saveUpdateCategory(Category category){
		return service.saveUpdateCategory(category)
				.flatMap(c -> Mono.just(ResponseEntity.ok().body(c)));
	}
	
	/**
	 * Get category by category parameters
	 * @param {@link {@link RequestBody}
	 * @return {@link List}
	 */
	@PostMapping("/search-by-params")
	public Flux<Category> getCategoriesByParams(@RequestBody Map<String, String> response) {
		return service.getCategoriesByParams(response.get("searchByName"), response.get("searchByType"));
	}
	
}
