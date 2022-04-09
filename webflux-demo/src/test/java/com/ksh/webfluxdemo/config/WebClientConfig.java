package com.ksh.webfluxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient webClient() {
		
		return WebClient.builder()
				.baseUrl("http://localhost:8080")
//				.defaultHeaders(h -> h.setBasicAuth("username","password"))
//				.filter((clientRequest, exchangeFunction) -> sessionToken(clientRequest, exchangeFunction))
				.filter(this::sessionToken)
				.build();
	}
	
	private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex){
		System.out.println("generating session token");
		ClientRequest clientRequest = ClientRequest.from(request).headers(h -> h.setBearerAuth("some-lengthy-jwt")).build();
		return ex.exchange(clientRequest);
	}
}
