package com.gibran.productservice.util;

import java.util.function.BiFunction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gibran.productservice.pojo.ResponsePojo;

public class GenerateCommonResponse {
	
	public static BiFunction<Object, HttpStatus, ResponseEntity<Object>> generateResponseEntityBiFunction = 
			(response, httpStatus) -> new ResponseEntity<Object>(response, httpStatus);

	public static BiFunction<String, String, ResponseEntity<Object>> generateCommonResponseObjectBiFunction  = 
			(responseCode, responseMessage) -> 
		generateResponseEntityBiFunction.apply(new ResponsePojo(responseCode, responseMessage), HttpStatus.OK);
		
	
			

	
}