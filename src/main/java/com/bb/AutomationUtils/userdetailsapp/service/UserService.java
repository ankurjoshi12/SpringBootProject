package com.bb.AutomationUtils.userdetailsapp.service;

import com.bb.AutomationUtils.userdetailsapp.model.UserDetails;

public interface UserService {

	UserDetails getUserDetails(String mobileNumber);
	UserDetails getUserDetailsByEmail(String email);
}
