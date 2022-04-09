package com.ksh.webfluxdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ksh.webfluxdemo.dto.MultiplyRequestDto;
import com.ksh.webfluxdemo.dto.Response;
import com.ksh.webfluxdemo.exception.InputValidationException;
import com.ksh.webfluxdemo.service.ReactiveMathService;

import reactor.core.publisher.Flux;
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
	
	public Mono<ServerResponse> tableHandler(ServerRequest serverRequest){
		int input = Integer.valueOf(serverRequest.pathVariable("input"));
		Flux<Response> responseFlux = this.mathService.multiplicationTable(input);
		return ServerResponse.ok().body(responseFlux, Response.class);
	}
	
	public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest){
		int input = Integer.valueOf(serverRequest.pathVariable("input"));
		Flux<Response> responseFlux = this.mathService.multiplicationTable(input);
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(responseFlux, Response.class);
	}
	
	public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest){
		Mono<MultiplyRequestDto> requestToMono = serverRequest.bodyToMono(MultiplyRequestDto.class);
		Mono<Response> responseMono = this.mathService.multiply(requestToMono);
		
		return ServerResponse.ok()
				.body(responseMono, Response.class);
	}
	
	public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest){
		int input = Integer.valueOf(serverRequest.pathVariable("input"));
		if(input < 10 || input > 20) {
//			InputFailedValidationResponse response = new InputFailedValidationResponse();
//			return ServerResponse.badRequest().bodyValue(response);
			return Mono.error(new InputValidationException(input));
		}
		Mono<Response> responseMono = this.mathService.findSquare(input);
		return ServerResponse.ok().body(responseMono, Response.class);
	}

}
