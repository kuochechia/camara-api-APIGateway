package com.taiwanmobile.pt.APIGateway.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taiwanmobile.pt.APIGateway.vo.PhoneNumberRequest;
import com.taiwanmobile.pt.APIGateway.vo.RoamingRequest;

@Service("ResourceService")
public class ResourceService {
	
	@Value("${resource.number.verification.verify.url}")
	private String numberVerificationVerifyURL;
	@Value("${resource.sim.swap.retrieve.date.url}")
	private String simSwapRetrieveDateURL;
	@Value("${resource.roaming.status.roaming.url}")
	private String roamingStatusRoamingURL;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public Map<String, Object> verify(String phoneNumberRequest, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token);
		
		PhoneNumberRequest pnRequest = new Gson().fromJson(phoneNumberRequest, PhoneNumberRequest.class);
    	HttpEntity<PhoneNumberRequest> requestEntity = new HttpEntity<PhoneNumberRequest>(pnRequest, headers);
    	ResponseEntity<String> resp = restTemplate.exchange(numberVerificationVerifyURL, HttpMethod.POST, requestEntity, String.class);
		
    	return new Gson().fromJson(resp.getBody(), new TypeToken<Map<String, Object>>(){}.getType());
	}
	
	
	public Map<String, Object> retrieveDate(String phoneNumberRequest, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token);
		
		PhoneNumberRequest pnRequest = new Gson().fromJson(phoneNumberRequest, PhoneNumberRequest.class);
    	HttpEntity<PhoneNumberRequest> requestEntity = new HttpEntity<PhoneNumberRequest>(pnRequest, headers);
    	ResponseEntity<String> resp = restTemplate.exchange(simSwapRetrieveDateURL, HttpMethod.POST, requestEntity, String.class);
		
    	return new Gson().fromJson(resp.getBody(), new TypeToken<Map<String, Object>>(){}.getType());
	}
	
	
	public Map<String, Object> roaming(String roamingRequest, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token);
		
		RoamingRequest rRequest = new Gson().fromJson(roamingRequest, RoamingRequest.class);
    	HttpEntity<RoamingRequest> requestEntity = new HttpEntity<RoamingRequest>(rRequest, headers);
    	ResponseEntity<String> resp = restTemplate.exchange(roamingStatusRoamingURL, HttpMethod.POST, requestEntity, String.class);
		
    	Map<String, Object> roamingResp = new Gson().fromJson(resp.getBody(), new TypeToken<Map<String, Object>>(){}.getType());
    	if(roamingResp.get("countryCode") != null) {
    		roamingResp.put("countryCode", ((Double) roamingResp.get("countryCode")).intValue());
    	}
    	
    	return roamingResp;
	}
}