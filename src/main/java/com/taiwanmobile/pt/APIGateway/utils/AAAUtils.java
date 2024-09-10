package com.taiwanmobile.pt.APIGateway.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class AAAUtils {
	private static final Logger logger = LoggerFactory.getLogger(AAAUtils.class);
	
	private static Pattern pattern = Pattern.compile("<\\w*\\s?>\\S*</\\w*>");
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Map<String, String> queryNumberState(String session, String msisdn, String sechashcode, String userP1) throws Exception {
		Map<String, String> result = new HashMap<String, String>();
		long st = new Date().getTime();
		
    	try {
//	    	HttpHeaders headers = new HttpHeaders();
//	    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//	    	
//	    	MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
//	    	params.add("applyChannel", "MobileConnect");
//	    	params.add("userID", msisdn);
//			if(StringUtils.isNotBlank(sechashcode)) {
//		    	params.add("sechashcode", sechashcode);
//			}
//			if(StringUtils.isNotBlank(userP1)) {
//		    	params.add("userP1", userP1);
//			}
//	    	
//	    	HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//	    	
//	    	ResponseEntity<String> response = restTemplate.exchange("http://172.30.2.200:8880/AAA/query_number_state.jsp", HttpMethod.POST, requestEntity, String.class);
//	    	
//	    	String resp = response.getBody();
//			logger.info("{},queryNumberState,{},{}",new Object[] {session,new Date().getTime()-st,resp.replaceAll("\\r", "").replaceAll("\\n", "")}  );
//			
//			if(response.getStatusCode() == HttpStatus.OK) {
    		String resp = "<?xml=\"1.0\" encoding=\"big5\"?><ReturnCode>0</ReturnCode>< ReturnDesc>OK</ReturnDesc><RtnCode>1</RtnCode><StatusCode>0</StatusCode><OpCode>46697</OpCode><nwCode>0</nwCode>";
    		
    			Matcher matcher = pattern.matcher(resp);
    			while(matcher.find()) {
    				String str = matcher.group();
    				String key = strSpilt(str,"<",">");
    				String value = strSpilt(str,">","<");
    				result.put(key.trim(), value.trim());
    			}
//        	}
	    } catch(Exception e) {
	    	logger.error("{},queryNumberState,{}",new Object[] {session, e.getMessage()});
	    }
    	logger.info("result: "+result);
    	return result; 
	}
	
	
	private String strSpilt(String line, String target, String end) {
		int s = line.indexOf(target) + target.length();
		int e = line.indexOf(end, s);
		
		return line.substring(s, e < 0 ? s : e);
	}

}