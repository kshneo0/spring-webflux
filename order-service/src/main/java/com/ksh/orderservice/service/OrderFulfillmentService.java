package com.ksh.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksh.orderservice.client.ProductClient;
import com.ksh.orderservice.client.UserClient;
import com.ksh.orderservice.dto.PurchaseOrderRequestDto;
import com.ksh.orderservice.dto.PurchaseOrderResponseDto;
import com.ksh.orderservice.dto.RequestContext;
import com.ksh.orderservice.repository.PurchaseOrderRepository;
import com.ksh.orderservice.util.EntityDtoUtil;

import reactor.core.publisher.Mono;

@Service
public class OrderFulfillmentService {
	
	@Autowired
	private PurchaseOrderRepository orderRepository;
	
	@Autowired
	private ProductClient productClient;
	
	@Autowired
	private UserClient userClient;
	
	public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono){
		requestDtoMono.map(RequestContext::new)
			.flatMap(this::productRequestResponse)
			.doOnNext(EntityDtoUtil::setTransactionRequestDto)
			.flatMap(this::userRequestResponse)
			.map(EntityDtoUtil::getPurchaseOrder)
			.map(this.orderRepository::save)
			
	}
	
	private Mono<RequestContext> productRequestResponse(RequestContext rc) {
		return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
			.doOnNext(rc::setProductDto)
			.thenReturn(rc);
	}
	
	private Mono<RequestContext> userRequestResponse(RequestContext rc){
		this.userClient.authorizeTransaction(rc.getTransactionRequestDto())
			.doOnNext(rc::setTransactionResponseDto)
			.thenReturn(rc);
	}
}
