package com.gibran.reviewservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gibran.reviewservice.model.ProductReviewsModel;

public interface ReviewsRepository extends MongoRepository<ProductReviewsModel, String>{
	
	@Query("{productId:'?0'}")
	Optional<ProductReviewsModel> fetchByProduct(String productId);
}
