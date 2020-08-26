package com.webFlux.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webFlux.models.Product;
import com.webFlux.models.ProductByUserCart;
import com.webFlux.models.UserProgram;
import com.webFlux.service.GeneralService;
import reactor.core.publisher.Mono;

@Component
public class ProductCartHandler {

	@Autowired
	private GeneralService service;
	
	public Mono<ServerResponse> getProductByCartUser(ServerRequest request) {
		Mono<UserProgram> user = request.bodyToMono(UserProgram.class);
		return user.flatMap(u -> {
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(service.getProductsByUserCart(u.getUserName()), Product.class);
		});
	}
			
	public Mono<ServerResponse> getProductsWithoutAmounts(ServerRequest request) {
		Mono<UserProgram> user = request.bodyToMono(UserProgram.class);
		return user.flatMap(u -> {
			return ServerResponse.ok()
					.body(service.getProductsWithoutAmounts(u.getUserName()), ProductByUserCart.class);
		});
	}
	
	public Mono<ServerResponse> deleteProductFromCart(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(uc -> {
			return ServerResponse.ok()
					.body(service.deleteProductByUserCart(uc.get("userName"), uc.get("id")), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> deleteAllProductsByProductIdFromCart(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(uc -> {
			return ServerResponse.ok()
					.body(service.deleteAllProductsByProductId(uc.get("userName"), uc.get("id")), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> deleteProductsFromCart(ServerRequest request) {
		Mono<UserProgram> user = request.bodyToMono(UserProgram.class);
		return user.flatMap(u -> {
			return ServerResponse.ok()
					.body(service.deleteAllProductsCartByUserName(u.getUserName()), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> addProductsToCart(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(pc -> {
			ProductByUserCart p = new ProductByUserCart();
			p.setProductId(pc.get("id"));
			p.setUserName(pc.get("userName"));
			return ServerResponse.ok()
					.body(service.addProductByUserCart(p), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> updateNewUserNameOnLists(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(updates -> {
			return ServerResponse.ok()
					.body(service.updateUserNameNewOnProductCartList(updates.get("userNameNew"), updates.get("userNameOld")), ResponseEntity.class);
		});
	}
}
