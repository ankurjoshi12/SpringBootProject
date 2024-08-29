package com.bb.AutomationUtils.userdetailsapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {

	@GetMapping("/swaggerGet")
	public void sample1(@RequestParam String org , @RequestParam int id) {
		
	}
	@GetMapping("/swaggerGet1")
	public void sample2(@RequestParam String org , @RequestParam int id) {
		
	}
	@GetMapping("/swaggerGet2")
	public void sample3(@RequestParam String org , @RequestParam int id) {
		
	}
}
