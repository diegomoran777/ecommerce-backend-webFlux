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
import com.webFlux.models.Product;
import com.webFlux.service.GeneralService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = {"*"})
public class ControllerRestProduct {

	@Autowired
	GeneralService service;
	
	
	/**
	 * Get all products
	 * @return {@link List}
	 */
	@GetMapping("/products")
	public Flux<Product> getProducts(){
		return service.findAllProducts();
	}
	
	/**
	 * Get product by id
	 * @param product
	 * @return {@link List}
	 */
	@PostMapping("/getproductid")
	public Mono<Product> getProductById(@RequestBody Product product){
		return service.findProductById(product.getId());
	}
	
	/**
	 * Get product by id
	 * @param product
	 * @return {@link Product}
	 */
	@PostMapping("/get-product")
	public Mono<Product> getProduct(@RequestBody Product product){
		return service.findProductById(product.getId());
	}
	
	/**
	 * Delete product by id
	 * @param id
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Boolean>> deleteProductById(@PathVariable String id){
		return service.deleteProductById(id);  
	}
	
	/**
	 * Get products by categoryId
	 * @param product
	 * @return {@link List}
	 */
	@PostMapping("/bycategoryid")
	public Flux<Product> getProductsByCategoryID(@RequestBody Product product){
		return service.findProductsByCategoryId(product.getId());
	}
	
	/**
	 * Get product by categoryId and productId
	 * @param product
	 * @return {@link List}
	 */
	@PostMapping("/bycategory-productid")
	public Mono<Product> getProductsByCategoryAndProductId(@RequestBody Product product){
		return service.findProductsByCategoryAndProductId(product.getCategoryId(), product.getId());
	}
	
	/**
	 * Save and update product
	 * @param product
	 * @return {@link ResponseEntity<product>}
	 */
	@PostMapping("/save-update")
	public Mono<ResponseEntity<Product>> saveUpdateProduct(Product product){
		return service.saveUpdateProduct(product)
				.flatMap(p -> Mono.just(ResponseEntity.ok().body(p)));
	}
	
	/**
	 * Get all types of product
	 * @return {@link Set}
	 */
	@GetMapping("/types")
	public Flux<String> getAllTypes(){
		return service.getAllTypes();
	}
	
	/**
	 * Get all types of productId
	 * @param id
	 * @return {@link Set}
	 */
	@GetMapping("/types/{id}")
	public Flux<String> getTypesById(@PathVariable String id){
		return service.getTypesByCategory(id);
	}
	
	/**
	 * Get product by param and CategoryId
	 * @param {@link RequestBody}
	 * @return {@link List}
	 */
	@PostMapping("/search-by-paramsid")
	public Flux<Product> getProductsByParamsId(@RequestBody BodyRequest body) {
		return service.getProductByParamsId(body.getSearchByName(), body.getSearchByNumberEx(), body.getSearchByType(), body.getCategoryId());
	}
	
	/**
	 * update new category name on list
	 * @param {@link Map<String, String>}
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/update-new-categoryname")
	public Mono<ResponseEntity<?>> updateNewCategoryNameOnLists(@RequestBody Map<String, String> res) {
		return service.updateCategoryNameNewProduct(res.get("categoryNameNew"), res.get("categoryNameOld"));
	}
	
	  
}
