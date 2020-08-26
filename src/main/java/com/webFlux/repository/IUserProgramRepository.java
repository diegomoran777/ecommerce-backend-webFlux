package com.webFlux.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.webFlux.models.UserProgram;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Interface of User repository that connect with database 
 * @author Diego Moran
 * @version: 1.0
 */
public interface IUserProgramRepository extends ReactiveMongoRepository<UserProgram, String> {
	/**
	 * Returns a user list by userName
	 * @return list userProgram
	 */
	Flux<UserProgram> findByOrderByUserName();
	
	/**
	 * Returns a userProgram by user id
	 * @return UserProgram
	 * @param id
	 */
	Mono<UserProgram> findById(String id);
	
	/**
	 * Returns a userProgram by userName
	 * @return UserProgram
	 * @param userName
	 */
	Mono<UserProgram> findByUserName(String userName);
	
	/**
	 * Returns a userProgram by email
	 * @return UserProgram
	 * @param email
	 */
	UserProgram findByEmail(String email);
	
	/**
	 * Verify if a user exists by userName
	 * @return boolean
	 * @param userName
	 */
	boolean existsUserProgramByUserName(String userName);
	
	/**
	 * Verify if a user exists by email
	 * @return boolean
	 * @param 
	 */
	boolean existsUserProgramByEmail(String email);
	
	/**
	 * Returns a userProgram list by specific parameters
	 * @return UserProgram list
	 * @param userName
	 * @param role
	 */
	@Query("{ 'userName' : { $regex: ?0, $options: 'i'}, 'role' : { $regex: ?1, $options: 'i'} }")
	Flux<UserProgram> searchByTwoParams(String userName, String role);
}
