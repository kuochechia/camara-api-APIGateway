package com.taiwanmobile.pt.APIGateway.vo;


public class OAuthRequest {
	
	private String client_id;
	private String response_type = "code";
	private String scope;
	private String redirect_uri;
	private String state;
	private String phone_number;
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getResponse_type() {
		return response_type;
	}
	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getRedirect_uri() {
		return redirect_uri;
	}
	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s", new Object[]{
				client_id,response_type,scope,redirect_uri,state,phone_number});
	}
}