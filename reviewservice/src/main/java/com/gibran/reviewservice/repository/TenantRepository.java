package com.gibran.reviewservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gibran.reviewservice.model.TenantDetailsModel;

public interface TenantRepository extends MongoRepository<TenantDetailsModel, String>{

}
