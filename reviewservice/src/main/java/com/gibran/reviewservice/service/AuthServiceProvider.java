package com.gibran.reviewservice.service;

import java.util.Arrays;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gibran.reviewservice.exceptions.TokenExpiredException;
import com.gibran.reviewservice.model.TenantDetailsModel;
import com.gibran.reviewservice.repository.TenantRepository;


@Service
public class AuthServiceProvider {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	TenantRepository tenantRepo;

	Supplier<TenantDetailsModel> tenantDetailsSupplier = () -> tenantRepo.findAll().get(0);

	public String verifyToken(String token, String loginId) {
		try {
			TenantDetailsModel tenantDetails = tenantDetailsSupplier.get();
			System.out.println(
					"tenant id " + tenantDetails.getTenantId() + "\ntenant Password " + tenantDetails.getTenantKey());
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			String tenantId = tenantDetails.getTenantId();
			String tenantPassword = tenantDetails.getTenantKey();
			HttpEntity<?> entity = new HttpEntity<>(headers);
			String res = restTemplate.exchange(
					"http://localhost:8081/authorize/user/verify?token={token}&login_id={loginId}&tenant_id={tenantId}&tenant_password={tenantPassword}&token_type=access",
					HttpMethod.GET, entity, String.class, token, loginId, tenantId, tenantPassword).getBody();
			System.out.println(res);
			return res;
		} catch (Exception e) {
			throw new TokenExpiredException();
		}
	}
}