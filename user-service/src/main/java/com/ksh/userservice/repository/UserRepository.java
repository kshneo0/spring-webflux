package com.ksh.userservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.ksh.userservice.entity.User;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {



}
