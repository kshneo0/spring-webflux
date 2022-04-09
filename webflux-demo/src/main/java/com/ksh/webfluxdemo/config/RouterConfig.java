package com.ksh.webfluxdemo.config;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ksh.webfluxdemo.dto.InputFailedValidationResponse;
import com.ksh.webfluxdemo.exception.InputValidationException;

import reactor.core.publisher.Mono;

@Configuration
public class RouterConfig {
	
	@Autowired
	private RequestHandler requestHandler;

	@Bean
	public RouterFunction<ServerResponse> highLevelRouter() {
		return RouterFunctions.route()
				.path("router", this::serverResponseRouterFunction)
				.build();
	}
	
//	@Bean
	private RouterFunction<ServerResponse> serverResponseRouterFunction() {
		return RouterFunctions.route()
				.GET("square/{input}",requestHandler::squareHandler)
				.GET("table/{input}",requestHandler::tableHandler)
				.GET("table/{input}/stream",requestHandler::tableStreamHandler)
				.POST("multiply",requestHandler::multiplyHandler)
				.GET("square/{input}/validation",requestHandler::squareHandlerWithValidation)
				.onError(InputValidationException.class, exceptionHandler())
				.build();
	}
	
/*	
	@Bean
	public RouterFunction<ServerResponse> serverResponseRouterFunction() {
		return RouterFunctions.route()
				.GET("router/square/{input}",requestHandler::squareHandler)
				.GET("router/table/{input}",requestHandler::tableHandler)
				.GET("router/table/{input}/stream",requestHandler::tableStreamHandler)
				.POST("router/multiply",requestHandler::multiplyHandler)
				.GET("router/square/{input}/validation",requestHandler::squareHandlerWithValidation)
				.onError(InputValidationException.class, exceptionHandler())
				.build();
	}
*/	
	private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler(){
		return (err, req) -> {
			InputValidationException ex = (InputValidationException)err;
			InputFailedValidationResponse response  = new InputFailedValidationResponse();
			response.setInput(ex.getInput());
			response.setMessage(ex.getMessage());
			response.setErrorCode(ex.getErrorCode());
			return ServerResponse.badRequest().bodyValue(response);
		};
	}
}
