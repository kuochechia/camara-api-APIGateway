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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.taiwanmobile.pt.APIGateway.utils.AAAUtils;
import com.taiwanmobile.pt.APIGateway.utils.AuthUtils;

@Service("OAuthService")
public class OAuthService {
	
	@Value("${apigw.access.token.url}")
	private String accessTokenURL;
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AAAUtils AAAUtils;
	@Autowired
	private AuthUtils AuthUtils;
	
	
	public Map<String, String> checkNumberState(String sessionId, String phoneNumber) throws Exception {
		return AAAUtils.queryNumberState(sessionId, phoneNumber, null, null);
	}
	
	
	public Map<String, String> getAuthorizeURL(String sessionId, String clientId, String redirectUri, String responseType, String scope, String phoneNumber) {
		return AuthUtils.getAuthorizeUrl(sessionId, clientId, redirectUri, responseType, scope, phoneNumber);
	}
	
	
	public String getToken(String authorizationCode, String redirectUri, String phoneNumber) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
    	MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>(); 
    	params.add("redirect_uri", redirectUri);
    	params.add("auth_code", authorizationCode);
    	params.add("state", phoneNumber);
    	
    	HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

    	ResponseEntity<String> tokenRequestResp = restTemplate.exchange(accessTokenURL, HttpMethod.POST, requestEntity, String.class);
    	
    	return tokenRequestResp.getBody();
	}
}