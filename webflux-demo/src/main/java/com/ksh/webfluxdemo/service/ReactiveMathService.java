package com.ksh.webfluxdemo.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.ksh.webfluxdemo.dto.MultiplyRequestDto;
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
				.delayElements(Duration.ofSeconds(1))
//				.doOnNext(i -> SleepUtil.sleepSeconds(1))
				.doOnNext(i-> System.out.println("math-service processing : " + i))
				.map(i -> new Response(i * input));

/*
 		//이것은 나쁜 방법. 브라우저에서 취소를 하면 멈추지 않는다.
		List<Response> list = IntStream.rangeClosed(1,10)
				.peek(i -> SleepUtil.sleepSeconds(1))
				.peek( i-> System.out.println("math-service processing : " + i))
				.mapToObj(i -> new Response(i * input))
				.collect(Collectors.toList());
		
		return Flux.fromIterable(list);
*/		
	}
	
	public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono){
		return dtoMono
				.map(dto -> dto.getFirst() * dto.getSecond())
				.map(Response::new);
	}
}
