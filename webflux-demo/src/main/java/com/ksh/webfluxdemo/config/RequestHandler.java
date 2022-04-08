package com.ksh.webfluxdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ksh.webfluxdemo.dto.Response;
import com.ksh.webfluxdemo.service.ReactiveMathService;

import reactor.core.publisher.Mono;

@Service
public class RequestHandler {
	
	@Autowired
	private ReactiveMathService mathService;
	
	public Mono<ServerResponse> squareHandler(ServerRequest serverRequest){
		int input = Integer.valueOf(serverRequest.pathVariable("input"));
		Mono<Response> responseMono = this.mathService.findSquare(input);
		return ServerResponse.ok().body(responseMono, Response.class);
	}

}
