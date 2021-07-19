package com.gibran.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gibran.productservice.pojo.ProductDetailsResponsePojo;
import com.gibran.productservice.util.GenerateCommonResponse;

@Service
public class ProductService {
	
	@Autowired
	private ProductDetailsUrlService productDetailsUrlService;

	public ResponseEntity<Object> getProductDetailsService(String productId){
		
		ProductDetailsResponsePojo productDetailsResponse = new ProductDetailsResponsePojo();
		productDetailsResponse.setProductDetails(productDetailsUrlService.getProductDetails(productId));
		productDetailsResponse.setProductReviews(productDetailsUrlService.getProductReviews(productId));
		
		return GenerateCommonResponse.generateResponseEntityBiFunction.apply(productDetailsResponse, HttpStatus.OK);
	}
}
