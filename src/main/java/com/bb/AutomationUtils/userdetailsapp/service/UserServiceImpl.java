package com.bb.AutomationUtils.userdetailsapp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bb.AutomationUtils.userdetailsapp.model.UserDetails;

@Service
public class UserServiceImpl implements UserService{
	// Simulated data source
    private static final Map<String, UserDetails> userDetailsMap = new HashMap<>();
    private static final Map<String, UserDetails> userDetailsByEmailMap = new HashMap<>();

    static {
        // Populate with some sample data
        userDetailsMap.put("7022243452", new UserDetails("7022243452", "Ankur Joshi", "ankur@bigbasket.com"));
        userDetailsByEmailMap.put("ankur@bigbasket.com", new UserDetails("7022243452", "John Doe", "ankur@bigbasket.com"));
        userDetailsMap.put("1234567890", new UserDetails("1234567890", "Dummy", "xyz@bigbasket"));
        userDetailsByEmailMap.put("xyz@bigbasket.com", new UserDetails("1234567890", "Dummy", "xyz@bigbasket"));
    }

    @Override
    public UserDetails getUserDetails(String mobileNumber) {
        return userDetailsMap.get(mobileNumber);
    }

    @Override
    public UserDetails getUserDetailsByEmail(String email) {
        return userDetailsByEmailMap.get(email);
    }
}
