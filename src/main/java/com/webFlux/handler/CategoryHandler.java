package com.webFlux.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webFlux.models.Category;
import com.webFlux.service.GeneralService;
import com.webFlux.service.ImageService;

import reactor.core.publisher.Mono;

@Component
public class CategoryHandler {

	@Autowired
	private GeneralService service;
	
	@Autowired
	ImageService imageService;
	
	public Mono<ServerResponse> getCategories(ServerRequest request) {
		return ServerResponse.ok()
				.body(service.findAllCategories(), Category.class);
	}
	
	public Mono<ServerResponse> getCategoriesByType(ServerRequest request) {
		String type = request.pathVariable("type");
		return ServerResponse.ok()
				.body(service.findByType(type), Category.class);
	}
	
	public Mono<ServerResponse> getCategoryById(ServerRequest request) {
		Mono<Category> category = request.bodyToMono(Category.class);
		return category.flatMap(c -> {
			return ServerResponse.ok()
					.body(service.findCategoryById(c.getId()), Category.class);
		});
	}
	
	public Mono<ServerResponse> getCategory(ServerRequest request) {
		Mono<Category> category = request.bodyToMono(Category.class);
		return category.flatMap(c -> {
			return ServerResponse.ok()
					.body(service.findCategoryById(c.getId()), Category.class);
		});
	}
	
	public Mono<ServerResponse> deleteCategoryProductsById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.body(service.deleteProductsFromCategory(id).then(service.deleteCategoryById(id)), ResponseEntity.class);
	}
	
	public Mono<ServerResponse> deleteCategoryImagesById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.body(imageService.deleteImagesFromCategory(id).then(service.deleteCategoryById(id)), ResponseEntity.class);
	}
	
	public Mono<ServerResponse> saveUpdateCategory(ServerRequest request) {
		Mono<Category> category = request.bodyToMono(Category.class);
		return category.flatMap(c -> {
			return ServerResponse.ok()
					.body(service.saveUpdateCategory(c)
							.flatMap(ca -> Mono.just(ResponseEntity.ok().body(ca))), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> getCategoriesByParams(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(params -> {
			return ServerResponse.ok()
					.body(service.getCategoriesByParams(params.get("searchByName"), params.get("searchByType")), Category.class);
		});
	}
}
