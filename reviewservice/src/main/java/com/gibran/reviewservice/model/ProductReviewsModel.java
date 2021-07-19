package com.gibran.reviewservice.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gibran.reviewservice.model.submodel.ReviewsSubModel;

@Document(collection = "productreviews")
public class ProductReviewsModel {
	
	@Id
	private String id;
	private String productId;
	private List<ReviewsSubModel> reviewsList;
	private double averageReview;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public double getAverageReview() {
		return averageReview;
	}
	public void setAverageReview(double averageReview) {
		this.averageReview = averageReview;
	}
	public List<ReviewsSubModel> getReviewsList() {
		return reviewsList;
	}
	public void setReviewsList(List<ReviewsSubModel> reviewsList) {
		this.reviewsList = reviewsList;
	}
}
