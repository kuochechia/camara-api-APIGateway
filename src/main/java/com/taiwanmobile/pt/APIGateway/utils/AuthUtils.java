package com.taiwanmobile.pt.APIGateway.utils;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class AuthUtils {
	private static final Logger logger = LoggerFactory.getLogger(AuthUtils.class);

	@Autowired
	private RestTemplate restTemplate;
	
	public Map<String, String> getAuthorizeUrl(String session, String clientId, String redirectUri, String resposeType, String scope, String phoneNumber) {
		Map<String, String> result = new HashMap<String, String>();
		
		try {
			HttpHeaders headers = new HttpHeaders();
			
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>(); 
	    	params.add("client_id", clientId);
	    	params.add("redirect_uri", redirectUri);
	    	params.add("response_type", resposeType);
	    	params.add("scope", scope);
	    	params.add("phone_number", phoneNumber);
	    	
	    	URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8442/Auth/api/authRequest")
	    			.queryParams(params)
	                .build()
	                .toUri();
	    	
	    	HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
	    	ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);

	    	String resp = response.getBody();
	    	if(response.getStatusCode() == HttpStatus.OK) {
	    		Type mapType = new TypeToken<Map<String, String>>(){}.getType();
	            Map<String, String> map = new Gson().fromJson(resp, mapType);
	    		result.put("returnCode", map.get("returnCode"));
	    		result.put("authorizeUrl", map.get("authorizeUrl"));
	    		
	    		logger.info("result: "+result);
	    	}
		} catch(Exception e) {
			logger.error("{},getAuthorizeUrl,{}",new Object[] {session, e.getMessage()});
		}
		return result;
	}
}