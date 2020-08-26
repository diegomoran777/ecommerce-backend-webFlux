package com.webFlux.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webFlux.dto.BodyRequest;
import com.webFlux.models.Product;
import com.webFlux.service.GeneralService;

import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

	@Autowired
	GeneralService service;
	
	public Mono<ServerResponse> getProducts(ServerRequest request) {
		return ServerResponse.ok()
				.body(service.findAllProducts(), Product.class);
	}
	
	public Mono<ServerResponse> getProductById(ServerRequest request) {
		Mono<Product> product = request.bodyToMono(Product.class);
		return product.flatMap(p -> {
			return ServerResponse.ok()
					.body(service.findProductById(p.getId()), Product.class);
		});
	}
	
	public Mono<ServerResponse> deleteProductById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.body(service.deleteProductById(id), ResponseEntity.class);
	}
	
	public Mono<ServerResponse> getProductsByCategoryID(ServerRequest request) {
		Mono<Product> product = request.bodyToMono(Product.class);
		return product.flatMap(p -> {
			return ServerResponse.ok()
					.body(service.findProductsByCategoryId(p.getId()), Product.class);
		});
	}
	
	public Mono<ServerResponse> getProductsByCategoryAndProductId(ServerRequest request) {
		Mono<Product> product = request.bodyToMono(Product.class);
		return product.flatMap(p -> {
			return ServerResponse.ok()
					.body(service.findProductsByCategoryAndProductId(p.getCategoryId(), p.getId()), Product.class);
		});
	}
	
	public Mono<ServerResponse> saveUpdateProduct(ServerRequest request) {
		Mono<Product> product = request.bodyToMono(Product.class);
		return product.flatMap(p -> {
			return ServerResponse.ok()
					.body(service.saveUpdateProduct(p)
							.flatMap(pr -> Mono.just(ResponseEntity.ok().body(pr))), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> getAllTypes(ServerRequest request) {
		return ServerResponse.ok()
				.body(service.getAllTypes(), String.class);
	}
	
	public Mono<ServerResponse> getTypesById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.body(service.getTypesByCategory(id), String.class);
	}
	
	public Mono<ServerResponse> getProductsByParamsId(ServerRequest request) {
		Mono<BodyRequest> bodyRequest = request.bodyToMono(BodyRequest.class);
		return bodyRequest.flatMap(params -> {
			return ServerResponse.ok()
					.body(service.getProductByParamsId(params.getSearchByName(), params.getSearchByNumberEx(), params.getSearchByType(), params.getCategoryId()), Product.class);
		});
	}
	
	public Mono<ServerResponse> updateNewCategoryNameOnLists(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(params -> {
			return ServerResponse.ok()
					.body(service.updateCategoryNameNewProduct(params.get("categoryNameNew"), params.get("categoryNameOld")), ResponseEntity.class);
		});
	}
	
}
