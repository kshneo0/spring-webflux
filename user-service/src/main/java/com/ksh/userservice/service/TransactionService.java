package com.ksh.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksh.userservice.dto.TransactionRequestDto;
import com.ksh.userservice.dto.TransactionResponseDto;
import com.ksh.userservice.dto.TransactionStatus;
import com.ksh.userservice.repository.UserRepository;
import com.ksh.userservice.repository.UserTransactionRepository;
import com.ksh.userservice.util.EntityDtoUtil;

import reactor.core.publisher.Mono;

@Service
public class TransactionService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserTransactionRepository transactionRepository;
	
	public Mono<TransactionResponseDto> createTransaction(final TransactionRequestDto requestDto){
		return this.userRepository.updateUserBalabce(requestDto.getUserId(), requestDto.getAmount())
				.filter(Boolean::booleanValue)
				.map(b -> EntityDtoUtil.toEntity(requestDto))
				.flatMap(this.transactionRepository::save)
				.map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
				.defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
	}
	
}
