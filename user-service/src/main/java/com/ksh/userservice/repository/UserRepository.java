package com.ksh.userservice.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ksh.userservice.entity.User;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
	
	@Modifying
	@Query( "update users " +
			"   set balance = balance - :amount" +
			" where id = :userId " + 
			"   and balance >= :amount"
	)
	Mono<Boolean> updateUserBalabce(int userId, int amt);



}
