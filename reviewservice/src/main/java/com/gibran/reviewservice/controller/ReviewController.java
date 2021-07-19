package com.gibran.reviewservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gibran.reviewservice.pojo.CreateReviewPojo;
import com.gibran.reviewservice.service.AuthServiceProvider;
import com.gibran.reviewservice.service.ReviewService;

@RestController
@RequestMapping(path = "review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private AuthServiceProvider authServiceProvider;

	@GetMapping("{product_id}")
	public ResponseEntity<Object> getReview(@PathVariable(name = "product_id", required = true) String productId){
		
		return reviewService.getReviews(productId);
	}
	
	@PostMapping("{product_id}")
	public ResponseEntity<Object> createReview(@PathVariable(name = "product_id", required = true) String productId,
											   @RequestParam(name = "access-token") String accessToken,
											   @RequestParam(name = "login-id") String loginId,
											   @RequestBody CreateReviewPojo createReviewRequest){
		
		String userId = authServiceProvider.verifyToken(accessToken, loginId);
		return reviewService.createReview(userId, productId, createReviewRequest);
	}
	
	@PatchMapping("{product_id}")
	public ResponseEntity<Object> updateReview(@PathVariable(name = "product_id", required = true) String productId,
											   @RequestParam(name = "access-token") String accessToken,
											   @RequestParam(name = "login-id") String loginId,
											   @RequestBody CreateReviewPojo updateReviewRequest){
		
		String userId = authServiceProvider.verifyToken(accessToken, loginId);
		return reviewService.updateReview(userId, productId, updateReviewRequest);
	}
	
	@DeleteMapping("{product_id}")
	public ResponseEntity<Object> deleteReview(@PathVariable(name = "product_id", required = true) String productId,
											   @RequestParam(name = "access-token") String accessToken,
											   @RequestParam(name = "login-id") String loginId){
		
		String userId = authServiceProvider.verifyToken(accessToken, loginId);
		return reviewService.deleteReview(userId, productId);
	}
}
