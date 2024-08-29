package com.bb.AutomationUtils.userdetailsapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bb.AutomationUtils.RestController.LearnigJsonReturn;

@RestController
public class jsonController {
	@GetMapping("/jsonreturn")
    public LearnigJsonReturn jsonreturn() {
        return new LearnigJsonReturn("Message","okay 200");
    }
}
