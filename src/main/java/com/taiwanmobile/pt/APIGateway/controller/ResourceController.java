package com.taiwanmobile.pt.APIGateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.taiwanmobile.pt.APIGateway.service.ResourceService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/ApiGateway/api")
public class ResourceController {
	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	private ResourceService resourceService;
	
	
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> verify(
			HttpServletRequest request, 
			HttpServletResponse response, 
			@RequestBody(required = false) String phoneNumberRequest) {
	
		logger.info("api gateway call Number Verification: verify...");
		logger.info("phoneNumberRequest: " + phoneNumberRequest);
	
		Map<String, Object> result = new HashMap<String, Object>();
	
		try {
			
			result = resourceService.verify(phoneNumberRequest, request.getHeader("Authorization"));
			
		} catch(Exception e) {
			result.put("status", 500);
			result.put("code", "INTERNAL");
			result.put("message", "Server error");
			
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/retrieve-date", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> retrieveDate(
			HttpServletRequest request, 
			HttpServletResponse response, 
			@RequestBody(required = false) String phoneNumberRequest) {
	
		logger.info("api gateway call SIM Swap: retrieve-date...");
		logger.info("phoneNumberRequest: " + phoneNumberRequest);
	
		Map<String, Object> result = new HashMap<String, Object>();
	
		try {
			
			result = resourceService.retrieveDate(phoneNumberRequest, request.getHeader("Authorization"));
			
		} catch(Exception e) {
			result.put("status", 500);
			result.put("code", "INTERNAL");
			result.put("message", "Server error");
			
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/roaming", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> roaming(
			HttpServletRequest request, 
			HttpServletResponse response, 
			@RequestBody(required = false) String roamingRequest) {
	
		logger.info("api gateway call Roaming Status: roaming...");
		logger.info("roamingRequest: " + roamingRequest);
	
		Map<String, Object> result = new HashMap<String, Object>();
	
		try {
			
			result = resourceService.roaming(roamingRequest, request.getHeader("Authorization"));

		} catch(Exception e) {
			result.put("status", 500);
			result.put("code", "INTERNAL");
			result.put("message", "Server error");
			
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}