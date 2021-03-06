package com.gibran.reviewservice.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gibran.reviewservice.util.GenerateCommonResponse;

@ControllerAdvice
public class TokenExceptionController {

	@ExceptionHandler(value = TokenExpiredException.class)
	public ResponseEntity<Object> exception(TokenExpiredException exception) {

		return GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("401", "Invalid Token");
	}

}