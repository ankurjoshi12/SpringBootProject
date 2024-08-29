package com.bb.AutomationUtils.userdetailsapp.model;

public class UserDetails {
	private String mobileNumber;
    private String name;
    private String email;

    public UserDetails(String mobileNumber, String name, String email) {
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.email = email;
    }

    // Getters and setters
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
