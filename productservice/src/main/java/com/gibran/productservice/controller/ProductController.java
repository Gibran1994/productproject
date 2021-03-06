package com.gibran.productservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gibran.productservice.service.ProductService;

@RestController
@RequestMapping(path = "/product")
public class ProductController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;

	@GetMapping("{product_id}")
	public ResponseEntity<Object> getProductDetails(@PathVariable(name = "product_id") String productId){
		
		LOG.info(this.getClass() + " : Controller. Fetching Product Details with Product ID : " + productId);
		return productService.getProductDetailsService(productId);
	}
}
