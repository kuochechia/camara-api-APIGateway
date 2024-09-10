package com.taiwanmobile.pt.APIGateway.vo;

public class Ipv4Address extends AbstractVo {
	private static final long serialVersionUID = -3459013718369626190L;

	private String publicAddress;
	private int publicPort;
	
	public String getPublicAddress() {
		return publicAddress;
	}
	public void setPublicAddress(String publicAddress) {
		this.publicAddress = publicAddress;
	}
	public int getPublicPort() {
		return publicPort;
	}
	public void setPublicPort(int publicPort) {
		this.publicPort = publicPort;
	}
}