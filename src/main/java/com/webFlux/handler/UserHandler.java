package com.webFlux.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webFlux.models.Purchase;
import com.webFlux.models.UserProgram;
import com.webFlux.service.GeneralService;

import reactor.core.publisher.Mono;

@Component
public class UserHandler {

	@Autowired
	GeneralService service;
	
	public Mono<ServerResponse> getUsers(ServerRequest request) {
		return ServerResponse.ok()
				.body(service.findAllUsers(), UserProgram.class);
	}
	
	public Mono<ServerResponse> getUserByUserName(ServerRequest request) {
		Mono<UserProgram> user = request.bodyToMono(UserProgram.class);
		return user.flatMap(u -> {
			return ServerResponse.ok()
					.body(service.findUserByUserName(u.getUserName()), UserProgram.class);
		});
	}
	
	public Mono<ServerResponse> existsUser(ServerRequest request) {
		Mono<UserProgram> user = request.bodyToMono(UserProgram.class);
		return user.flatMap(u -> {
			return ServerResponse.ok()
					.body(service.existsUserByName(u.getUserName())
							.flatMap(b -> Mono.just(ResponseEntity.ok().body(b))), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> existsUserByEmail(ServerRequest request) {
		Mono<UserProgram> user = request.bodyToMono(UserProgram.class);
		return user.flatMap(u -> {
			return ServerResponse.ok()
					.body(service.existsUserByEmail(u.getEmail())
							.flatMap(b -> Mono.just(ResponseEntity.ok().body(b))), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> deleteUserById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.body(service.deleteUserById(id), ResponseEntity.class);
	}
	
	public Mono<ServerResponse> saveUpdateUser(ServerRequest request) {
		Mono<UserProgram> user = request.bodyToMono(UserProgram.class);
		return user.flatMap(u -> {
			return ServerResponse.ok()
					.body(service.saveUpdateUser(u)
							.flatMap(us -> Mono.just(ResponseEntity.ok().body(us))), ResponseEntity.class);
			});
	}
	
	public Mono<ServerResponse> getUsersByParams(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(params -> {
			return ServerResponse.ok()
					.body(service.getUsersByParams(params.get("userName"), params.get("role")), UserProgram.class);
		});
	}
	
	public Mono<ServerResponse> getPurchassesFromUser(ServerRequest request) {
		Mono<UserProgram> user = request.bodyToMono(UserProgram.class);
		return user.flatMap(u -> {
			return ServerResponse.ok()
					.body(service.getPurchasesFromUser(u.getUserName()), Purchase.class);
			});
	}
	
	public Mono<ServerResponse> getPurchasesByParams(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(params -> {
			return ServerResponse.ok()
					.body(service.getPurchasesFromUser(params.get("userName"))
							.filter(p -> p.getDate_approved()
									.contains(params.get("date_approved"))), Purchase.class);
		});
	}
}