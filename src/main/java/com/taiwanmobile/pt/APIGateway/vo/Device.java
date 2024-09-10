package com.taiwanmobile.pt.APIGateway.vo;

public class Device extends AbstractVo {
	private static final long serialVersionUID = -2936345976267555841L;
	
	private String phoneNumber;
	private String networkAccessIdentifier;
	private Ipv4Address ipv4Address;
	private String ipv6Address;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getNetworkAccessIdentifier() {
		return networkAccessIdentifier;
	}
	public void setNetworkAccessIdentifier(String networkAccessIdentifier) {
		this.networkAccessIdentifier = networkAccessIdentifier;
	}
	public Ipv4Address getIpv4Address() {
		return ipv4Address;
	}
	public void setIpv4Address(Ipv4Address ipv4Address) {
		this.ipv4Address = ipv4Address;
	}
	public String getIpv6Address() {
		return ipv6Address;
	}
	public void setIpv6Address(String ipv6Address) {
		this.ipv6Address = ipv6Address;
	}
}