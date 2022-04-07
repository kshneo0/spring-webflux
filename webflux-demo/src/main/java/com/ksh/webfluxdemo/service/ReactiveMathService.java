package com.ksh.webfluxdemo.service;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ksh.webfluxdemo.dto.Response;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {

	public Mono<Response> findSquare(int input){
		return Mono.fromSupplier( ()-> input * input)
				.map(v -> new Response(v));
	}
	
	public Flux<Response> multiplicationTable(int input){
		return Flux.range(1,10)
				.doOnNext(i -> SleepUtil.sleepSeconds(1))
				.doOnNext(i-> System.out.println("math-service processing : " + i))
				.map(i -> new Response(i * input));
	}
}
