package com.webFlux.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webFlux.dto.BodyRequest;
import com.webFlux.models.Image;
import com.webFlux.service.ImageService;

import reactor.core.publisher.Mono;

@Component
public class ImageHandler {

	@Autowired
	ImageService service;
	
	public Mono<ServerResponse> getImages(ServerRequest request) {
		return ServerResponse.ok()
				.body(service.findAllImages(), Image.class);
	}
	
	public Mono<ServerResponse> getImageById(ServerRequest request) {
		Mono<Image> image = request.bodyToMono(Image.class);
		return image.flatMap(i -> {
			return ServerResponse.ok()
					.body(service.findImageById(i.getId()), Image.class);
		});
	}
	
	public Mono<ServerResponse> deleteImageById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.body(service.deleteImageById(id), ResponseEntity.class);
	}
	
	public Mono<ServerResponse> getImagesByCategoryID(ServerRequest request) {
		Mono<Image> image = request.bodyToMono(Image.class);
		return image.flatMap(i -> {
			return ServerResponse.ok()
					.body(service.findImagesByCategoryId(i.getId()), Image.class);
		});
	}
	
	public Mono<ServerResponse> getImagesByCategoryAndImageId(ServerRequest request) {
		Mono<Image> image = request.bodyToMono(Image.class);
		return image.flatMap(i -> {
			return ServerResponse.ok()
					.body(service.findImagesByCategoryAndImageId(i.getCategoryId(), i.getId()), Image.class);
		});
	}
	
	public Mono<ServerResponse> saveUpdateImage(ServerRequest request) {
		Mono<Image> image = request.bodyToMono(Image.class);
		return image.flatMap(i -> {
			return ServerResponse.ok()
					.body(service.saveUpdateImage(i)
							.flatMap(im -> Mono.just(ResponseEntity.ok().body(im))), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> getAllTypes(ServerRequest request) {
		return ServerResponse.ok()
				.body(service.getAllTypesFromImage(), String.class);
	}
	
	public Mono<ServerResponse> getTypesById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.body(service.getTypesImageByCategory(id), String.class);
	}
	
	public Mono<ServerResponse> getImagesByParamsId(ServerRequest request) {
		Mono<BodyRequest> bodyRequest = request.bodyToMono(BodyRequest.class);
		return bodyRequest.flatMap(br -> {
			return ServerResponse.ok()
					.body(service.getImageByParamsId(br.getSearchByName(), br.getSearchByType(), br.getCategoryId()), Image.class);
		});
	}
	
	public Mono<ServerResponse> updateNewCategoryNameOnLists(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(params -> {
			return ServerResponse.ok()
					.body(service.updateCategoryNameNewImage(params.get("categoryNameNew"), params.get("categoryNameOld")), ResponseEntity.class);
		});
	}

}
