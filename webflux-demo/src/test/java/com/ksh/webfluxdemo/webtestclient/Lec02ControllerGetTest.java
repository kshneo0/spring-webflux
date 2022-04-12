package com.ksh.webfluxdemo.webtestclient;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.ksh.webfluxdemo.controller.ReactiveMathController;
import com.ksh.webfluxdemo.dto.Response;
import com.ksh.webfluxdemo.service.ReactiveMathService;

import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class Lec02ControllerGetTest {

    @Autowired
    private WebTestClient client;
    
    @MockBean
    private ReactiveMathService reactiveMathService;
    
    @Test
    public void singleResponseTest(){

    	Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.empty());
//    	Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new Response(25)));
    	
        this.client
                .get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
//                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(25));
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(-1));

    }
}
