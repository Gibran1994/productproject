package com.gibran.productservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gibran.productservice.util.GenerateCommonResponse;

@ControllerAdvice
public class CommonExceptionController {

	@ExceptionHandler(value = CommonException.class)
	public ResponseEntity<Object> exception(CommonException exception){
		
		return GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("444", "Unknown Error Occured");
	}
}
