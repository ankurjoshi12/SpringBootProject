package com.bb.AutomationUtils.RestController;

public class LearnigJsonReturn {

	
	String status ;
	String message ; 
	
	public LearnigJsonReturn(String message, String status) {
		this.message = message;
		this.status = status;
	}
	
	
	@Override
	public String toString() {
		return "LearnigJsonReturn [message=" + message + ", status=" + status + "]";
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
}
