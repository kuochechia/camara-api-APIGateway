package com.taiwanmobile.pt.APIGateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taiwanmobile.pt.APIGateway.service.OAuthService;
import com.taiwanmobile.pt.APIGateway.vo.OAuthRequest;
import com.taiwanmobile.pt.APIGateway.vo.TokenRequest;


@RestController
@RequestMapping(value = "/ApiGateway/api")
public class OAuthController {
	private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);

	@Autowired
	private OAuthService oAuthService;
	
	
	@RequestMapping(value = "/authRequest", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> authRequest(HttpSession session, HttpServletResponse response, @ModelAttribute OAuthRequest req) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			String phoneNumber = req.getPhone_number().replaceFirst("\\+", "").trim();
			if(phoneNumber.startsWith("886")) {
				// call AAA
				Map<String, String> stateMap = oAuthService.checkNumberState(session.getId(), phoneNumber);
				logger.info("stateMap: "+stateMap);
				
				if("1".equals(stateMap.get("RtnCode"))) {
					Map<String, String> authMap = oAuthService.getAuthorizeURL(session.getId(), req.getClient_id(), req.getRedirect_uri(), 
							req.getResponse_type(), req.getScope(), phoneNumber);
					logger.info("authMap: "+authMap);
					
					if("00".equals(authMap.get("returnCode"))) {
						result.put("returnCode", "00");
						result.put("authorizeUrl", authMap.get("authorizeUrl"));
					} else {
						//Exception
					}
				} else {
					//call TWCA
					logger.info("TWCA");
				}
			} else {
				//call BAEx
				logger.info("BAEx");
			}
		} catch(Exception e) {
			
		}
		return result;
	}
	
	
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	@ResponseBody
	public String tokenRequest(HttpServletRequest request, HttpServletResponse response, @ModelAttribute TokenRequest req) {
		String token = null;
		response.setHeader("Authorization", request.getHeader("Authorization"));
		
		if(req.getState().startsWith("886") || req.getState().startsWith("09")) {
			token = oAuthService.getToken(req.getAuth_code(), req.getRedirect_uri(), req.getState());
		}
		return token;
	}
}