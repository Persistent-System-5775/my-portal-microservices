package com.userservice.exception;

public class JobNotFoundException extends Exception {

private static final long serialVersionUID = 1L;
	
	public JobNotFoundException(String msg){  
		  super(msg);  
	}
}
