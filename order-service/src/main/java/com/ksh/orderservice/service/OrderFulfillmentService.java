package com.ksh.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksh.orderservice.client.ProductClient;
import com.ksh.orderservice.client.UserClient;
import com.ksh.orderservice.dto.PurchaseOrderRequestDto;
import com.ksh.orderservice.dto.PurchaseOrderResponseDto;
import com.ksh.orderservice.dto.RequestContext;

import reactor.core.publisher.Mono;

@Service
public class OrderFulfillmentService {
	
	@Autowired
	private ProductClient productClient;
	
	@Autowired
	private UserClient userClient;
	
	public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono){
		requestDtoMono.map(RequestContext::new)
			.flatMap(this::productRequestResponse)
	}
	
	private Mono<RequestContext> productRequestResponse(RequestContext rc) {
		return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
			.doOnNext(rc::setProductDto)
			.thenReturn(rc);
	}
}
