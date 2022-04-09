package com.ksh.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.ksh.webfluxdemo.dto.Response;

public class Lec01GetSingleResponseTest extends BaseTest{
	
	@Autowired
	private WebClient webClient;
	
	@Test
	public void blockTest() {
		Response response = this.webClient
			.get()
			.uri("/reactive-math/square/{number}", 5)
			.retrieve()
			.bodyToMono(Response.class)	//Mono<Response>
			.block();
		
		System.out.println(response);
	}

}
