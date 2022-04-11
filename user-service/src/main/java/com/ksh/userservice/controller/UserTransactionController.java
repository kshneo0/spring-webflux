package com.ksh.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksh.userservice.dto.TransactionRequestDto;
import com.ksh.userservice.dto.TransactionResponseDto;
import com.ksh.userservice.service.TransactionService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono){
		return requestDtoMono.flatMap(this.transactionService::createTransaction);
	}
}
