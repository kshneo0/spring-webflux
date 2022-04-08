package com.ksh.webfluxdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ksh.webfluxdemo.dto.Response;
import com.ksh.webfluxdemo.exception.InputValidationException;
import com.ksh.webfluxdemo.service.ReactiveMathService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {

    @Autowired
    private ReactiveMathService mathService;

    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input){
    	if(input < 10 || input > 20)
            throw new InputValidationException(input);
        return this.mathService.findSquare(input);
    }

}
