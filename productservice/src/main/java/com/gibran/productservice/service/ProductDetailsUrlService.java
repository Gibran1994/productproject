package com.gibran.productservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gibran.productservice.exception.CommonException;



@Service
public class ProductDetailsUrlService {
	
	@Autowired
	private RestTemplate restTemplate;

	public Object getProductDetails(String productId) {
		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			HttpEntity<?> entity = new HttpEntity<>(headers);
			 ResponseEntity<String> res = restTemplate.exchange(
					"https://www.adidas.co.uk/api/products/{productId}",
					HttpMethod.GET, entity, String.class, productId);
			if(res.getStatusCodeValue()==200) {
				return res.getBody();
			}else {
				throw new Exception();
			}
		}catch (Exception e) {
			throw new CommonException();
		}
	}
	
	public Object getProductReviews(String productId) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			
			HttpEntity<?> entity = new HttpEntity<>(headers);
			 ResponseEntity<String> res = restTemplate.exchange(
					"http://localhost:8080/reviewservice/review/{productId}",
					HttpMethod.GET, entity, String.class, productId);
			if(res.getStatusCodeValue()==200) {
				return res.getBody();
			}else {
				throw new Exception();
			}
		}catch (Exception e) {
			throw new CommonException();
		}
	}
}
