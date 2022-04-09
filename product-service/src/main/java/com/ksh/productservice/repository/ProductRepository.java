package com.ksh.productservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.ksh.productservice.entity.Product;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>{

}
