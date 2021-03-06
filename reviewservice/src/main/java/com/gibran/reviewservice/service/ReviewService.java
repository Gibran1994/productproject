package com.gibran.reviewservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gibran.reviewservice.model.ProductReviewsModel;
import com.gibran.reviewservice.model.submodel.ReviewsSubModel;
import com.gibran.reviewservice.pojo.CreateReviewPojo;
import com.gibran.reviewservice.repository.ReviewsRepository;
import com.gibran.reviewservice.util.GenerateCommonResponse;

@Service
public class ReviewService {

	@Autowired
	ReviewsRepository reviewRepository;
	
	Function<String, Optional<ProductReviewsModel>> getProductReviewsFunction = (productId) -> 
		 reviewRepository.fetchByProduct(productId);

	public ResponseEntity<Object> getReviews(String productId) {
		Optional<ProductReviewsModel> productReviewsOptional = getProductReviewsFunction.apply(productId);
		
		return productReviewsOptional.isPresent()&&productReviewsOptional.get().getReviewsList()!=null&&!productReviewsOptional.get().getReviewsList().isEmpty()?
				GenerateCommonResponse.generateResponseEntityBiFunction.apply(productReviewsOptional.get(),HttpStatus.OK)
				:GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("421","No Reviews Found For Given Product ID");		
	}
	
	public ResponseEntity<Object> createReview(String userId, String productId, CreateReviewPojo createReviewRequest){
		
		Optional<ProductReviewsModel> productReviewsOptional = getProductReviewsFunction.apply(productId);
		ProductReviewsModel productReviews;
		if(!productReviewsOptional.isPresent()) {
			productReviews = new ProductReviewsModel();
			productReviews.setReviewsList(new ArrayList<ReviewsSubModel>());
			productReviews.setProductId(productId);
		}else {
			productReviews = productReviewsOptional.get();
		}
		
		//Check if review with userID already exists
		List<ReviewsSubModel> reviewsList = productReviews.getReviewsList();
		if(getExistingUserReview(userId, reviewsList)<0){
			return GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("401", "User's review already exists. Please select edit review option");
		}
		ReviewsSubModel reviewSubModel = new ReviewsSubModel();
		reviewSubModel.setRating(createReviewRequest.getRating()>5?5:createReviewRequest.getRating()<1?1:createReviewRequest.getRating());
		reviewSubModel.setReview(createReviewRequest.getReview());
		reviewSubModel.setUserId(userId);
		
		reviewsList.add(reviewSubModel);
		productReviews.setAverageReview(getAverageReviews(reviewsList));
		reviewRepository.save(productReviews);
		return GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("211","Review Created Successfully");
	}
	
	double getAverageReviews(List<ReviewsSubModel> reviewsList) {
		
		double sum = 0;
		for(int i=0; i<reviewsList.size();i++) {
			sum += reviewsList.get(i).getRating();
		}
		return sum/reviewsList.size();
	}
	
	public ResponseEntity<Object> updateReview(String userId, String productId, CreateReviewPojo updateReviewRequest){
		
		Optional<ProductReviewsModel> productReviewsOptional = getProductReviewsFunction.apply(productId);
		if(!productReviewsOptional.isPresent()) {
			return GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("422", "Invalid Request");
		}
		
		ProductReviewsModel productReviews = productReviewsOptional.get();
		List<ReviewsSubModel> reviewsList = productReviews.getReviewsList();
		int userReviewIndex = getExistingUserReview(userId, reviewsList);
		
		if(userReviewIndex>=0) {
			ReviewsSubModel userReview = reviewsList.get(userReviewIndex);
			userReview.setRating(updateReviewRequest.getRating()>5?5:updateReviewRequest.getRating()<1?1:updateReviewRequest.getRating());
			userReview.setReview(updateReviewRequest.getReview());
			reviewsList.set(userReviewIndex, userReview);
			productReviews.setReviewsList(reviewsList);
			reviewRepository.save(productReviews);
			return GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("211","Review Updated Successfully");
		}else {
			return GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("413","User's review for product does not exist");
		}
	}
	
	int getExistingUserReview(String userId, List<ReviewsSubModel> reviewsList) {
		
		for(int i=0; i<reviewsList.size();i++) {
			if(userId.equals(reviewsList.get(i).getUserId())) return i;
		}
		return -1;
	}
	
	public ResponseEntity<Object> deleteReview(String userId, String productId){
		Optional<ProductReviewsModel> productReviewsOptional = getProductReviewsFunction.apply(productId);
		if(!productReviewsOptional.isPresent()) {
			return GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("422", "Invalid Request");
		}
		ProductReviewsModel productReviews = productReviewsOptional.get();
		List<ReviewsSubModel> reviewsList = productReviews.getReviewsList();
		boolean isDeleted = false;
		for(int i=0; i<reviewsList.size();i++) {
			if(userId.equals(reviewsList.get(i).getUserId())) { 
				reviewsList.remove(i); 
				isDeleted =  true;
				productReviews.setReviewsList(reviewsList);
				reviewRepository.save(productReviews);
				break;
			}
		}
		
		return isDeleted? GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("212", "Review deleted successfully") 
						: GenerateCommonResponse.generateCommonResponseObjectBiFunction.apply("413","User's review for product does not exist");
	}
	
	
}
