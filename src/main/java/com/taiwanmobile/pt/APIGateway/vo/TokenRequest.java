package com.taiwanmobile.pt.APIGateway.vo;


public class TokenRequest {
	
	private String client_id;
	private String scope;
	private String redirect_uri;
	private String auth_code;
	private String state;
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
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
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s", new Object[]{
				client_id,redirect_uri,scope,auth_code,state});
	}
}