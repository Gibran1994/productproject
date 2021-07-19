package com.gibran.reviewservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tenantdetails")
public class TenantDetailsModel {

	@Id
	private String id;
	private String tenantId;
	private String tenantKey;
	
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantKey() {
		return tenantKey;
	}
	public void setTenantKey(String tenantKey) {
		this.tenantKey = tenantKey;
	}
}
