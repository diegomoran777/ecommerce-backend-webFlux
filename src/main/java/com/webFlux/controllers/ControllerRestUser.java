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

import com.webFlux.models.Purchase;
import com.webFlux.models.UserProgram;
import com.webFlux.service.GeneralService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"*"})
public class ControllerRestUser {

	@Autowired
	GeneralService service;
	
	/**
	 * Get all users
	 * @return {@link List}
	 */
	@GetMapping("/users")
	public Flux<UserProgram> getUsers(){
		return service.findAllUsers();
	}
	
	/**
	 * Get users by user name
	 * @param {@link RequestBody}
	 * @return {@link UserProgram}
	 */
	@PostMapping("/getuser")
	public Mono<UserProgram> getUserByUserName(@RequestBody UserProgram user){
		return service.findUserByUserName(user.getUserName());
	}
	
	/**
	 * Get users by user name
	 * @param {@link RequestBody}
	 * @return {@link List}
	 */
	@PostMapping("/get-user")
	public Mono<UserProgram> getUser(@RequestBody UserProgram user){
		return service.findUserByUserName(user.getUserName());
	}
	
	/**
	 * Verify if the specific user exists by userName
	 * @param {@link RequestBody}
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/exists")
	public Mono<ResponseEntity<?>> existsUser(@RequestBody UserProgram user) {
		return service.existsUserByName(user.getUserName())
				.flatMap(b -> Mono.just(ResponseEntity.ok().body(b)));
	}
	
	/**
	 * Verify if the specific user exists by email
	 * @param {@link RequestBody}
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/exists-email")
	public Mono<ResponseEntity<?>> existsUserByEmail(@RequestBody UserProgram user) {
		return service.existsUserByEmail(user.getEmail())
				.flatMap(b -> Mono.just(ResponseEntity.ok().body(b)));
	}
	
	/**
	 * Delete user by id
	 * @param {@link id}
	 * @return {@link ResponseEntity}
	 */
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Boolean>> deleteUserById(@PathVariable String id){
		return service.deleteUserById(id);  
	}
	
	/**
	 * Save or update user
	 * @param {@link UserProgam}
	 * @return {@link ResponseEntity}
	 */
	@PostMapping("/save-update")
	public Mono<ResponseEntity<UserProgram>> saveUpdateUser(UserProgram user){
		return service.saveUpdateUser(user)
				.flatMap(u -> Mono.just(ResponseEntity.ok().body(u)));
	}
	
	/**
	 * Get users by specific parameters
	 * @param {@link RequestBody}
	 * @return {@link List}
	 */
	@PostMapping("/search-by-params")
	public Flux<UserProgram> getUsersByParams(@RequestBody Map<String, String> response) {
		return service.getUsersByParams(response.get("userName"), response.get("role"));
	}
	
	/**
	 * Get purchases from user
	 * @param {@link RequestBody}
	 * @return {@link UserProgram}
	 */
	@PostMapping("/purchases")
	public Flux<Purchase> getPurchassesFromUser(@RequestBody UserProgram user){
		return service.getPurchasesFromUser(user.getUserName());
	}
	
	/**
	 * Get purchases by specific parameters
	 * @param {@link RequestBody}
	 * @return {@link List}
	 */
	@PostMapping("/search-by-date")
	public Flux<Purchase> getPurchasesByParams(@RequestBody Map<String, String> response) {
		return service.getPurchasesFromUser(response.get("userName"))
				.filter(p -> p.getDate_approved().contains(response.get("date_approved")));
	}
}
