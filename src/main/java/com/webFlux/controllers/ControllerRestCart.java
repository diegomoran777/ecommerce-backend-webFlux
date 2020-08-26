package com.webFlux.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webFlux.models.Product;
import com.webFlux.models.ProductByUserCart;
import com.webFlux.models.UserProgram;
import com.webFlux.service.GeneralService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cartuser")
@CrossOrigin(origins = {"*"})
public class ControllerRestCart {
	
	@Autowired
	GeneralService service;
	
	@PostMapping("/cart-products")
	public Flux<Product> getProductByCartUser(@RequestBody UserProgram user) {
		return service.getProductsByUserCart(user.getUserName());
	}
	
	@PostMapping("/cart-products-length")
	public Flux<ProductByUserCart> getProductsWithoutAmounts(@RequestBody UserProgram user) {
		return service.getProductsWithoutAmounts(user.getUserName());
	}
	
	@PostMapping("/delete-product")
	public Mono<ResponseEntity<Void>> deleteProductFromCart(@RequestBody Map<String, String> res) {
		return service.deleteProductByUserCart(res.get("userName"), res.get("id"));
	}
	
	@PostMapping("/delete-productsid")
	public Mono<ResponseEntity<?>> deleteAllProductsByProductIdFromCart(@RequestBody Map<String, String> res) {
		return service.deleteAllProductsByProductId(res.get("userName"), res.get("id"));
	}
	
	@PostMapping("/delete-products")
	public Mono<ResponseEntity<?>> deleteProductsFromCart(@RequestBody UserProgram user) {
		return service.deleteAllProductsCartByUserName(user.getUserName());
	}
	
	@PostMapping("/add-product")
	public Mono<ResponseEntity<?>> addProductsToCart(@RequestBody Map<String, String> res) {
		ProductByUserCart p = new ProductByUserCart();
		p.setProductId(res.get("id"));
		p.setUserName(res.get("userName"));
		return service.addProductByUserCart(p);
	}
	
	@PostMapping("/update-new-username")
	public Mono<ResponseEntity<?>> updateNewUserNameOnLists(@RequestBody Map<String, String> res) {
		return service.updateUserNameNewOnProductCartList(res.get("userNameNew"), res.get("userNameOld"));
	}
	

}
