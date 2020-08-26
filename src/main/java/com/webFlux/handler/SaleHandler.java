package com.webFlux.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webFlux.models.Sale;
import com.webFlux.service.GeneralService;

import reactor.core.publisher.Mono;

@Component
public class SaleHandler {

	@Autowired
	GeneralService service;
	
	public Mono<ServerResponse> getSales(ServerRequest request) {
		return ServerResponse.ok()
				.body(service.getSales(), Sale.class);
	}
	
	public Mono<ServerResponse> getSaleById(ServerRequest request) {
		Mono<Sale> sale = request.bodyToMono(Sale.class);
		return sale.flatMap(s -> {
			return ServerResponse.ok()
					.body(service.findSaleBYId(s.getId()), Sale.class);
		});
	}
	
	public Mono<ServerResponse> deleteSaleById(ServerRequest request) {
		String id = request.pathVariable("id");
		return ServerResponse.ok()
				.body(service.deleteSaleById(id), ResponseEntity.class);
	}
	
	public Mono<ServerResponse> saveUpdateSale(ServerRequest request) {
		Mono<Sale> sale = request.bodyToMono(Sale.class);
		return sale.flatMap(s -> {
			return ServerResponse.ok()
					.body(service.saveUpdateSale(s)
							.flatMap(sa -> Mono.just(ResponseEntity.ok().body(sa))), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> getSalesByParams(ServerRequest request) {
		Mono<Sale> sale = request.bodyToMono(Sale.class);
		return sale.flatMap(s -> {
			return ServerResponse.ok()
					.body(service.getSalesByParams(s.getDate_approved(), s.getUserName(), s.getUserMail()), Sale.class);
		});
	}
	
	public Mono<ServerResponse> updateNewUserNameOnLists(ServerRequest request) {
		Mono<Map<String, String>> res = request.bodyToMono(Map.class);
		return res.flatMap(params -> {
			return ServerResponse.ok()
					.body(service.updateUserNameFromSales(params.get("userNameNew"), params.get("userNameOld")), ResponseEntity.class);
		});
	}
	
	public Mono<ServerResponse> getSalesByDelivered(ServerRequest request) {
		Mono<Sale> sale = request.bodyToMono(Sale.class);
		return sale.flatMap(s -> {
			return ServerResponse.ok()
					.body(service.findByDelivered(s.isDelivered()), Sale.class);
		});
	}
	
	public Mono<ServerResponse> getTotalsMonthByYear(ServerRequest request) {
		Mono<String> year = request.bodyToMono(String.class);
		return year.flatMap(y -> {
			return ServerResponse.ok()
					.body(service.getTotalMonthsByYear(y), Float.class); 
		});
	}
}
