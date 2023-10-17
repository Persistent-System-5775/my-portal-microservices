package com.userservice.request;

import java.io.Serializable;

public class BaseRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String requestType;

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	@Override
	public String toString() {
		return "BaseRequest [requestType=" + requestType + "]";
	}
	
	
}
