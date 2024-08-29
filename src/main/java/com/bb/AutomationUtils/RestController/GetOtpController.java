package com.bb.AutomationUtils.RestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GetOtpController {

    @Value("${owner.name}")
    private String owner ;
    @Value("${team.name}")
    private String teamN ;

//    @GetMapping("/")
//    public String getOtp(){
//        return "this is the OTP";
//    }
    @GetMapping("/ankur")
    public String getOwner(){
        return "My Name is Ankur ";
    }
    @GetMapping("/team")
    public String teamDetail(){
        return teamN + "{"
        		+ " \"base_url\":\"https://qas31.bigbasket.com/media/assets/\",\n"
        		+ "      \"default_logo\":\"voucher_default_logo.jpg\"}";
    }
    
    @RequestMapping(method = RequestMethod.GET , path = "/hello")
    public String hello() {
    	return "Hello World";
    }
    @GetMapping("/returnjson")
    public LearnigJsonReturn getjson() {
    	return new LearnigJsonReturn("Api Success", "ok 200");
    }
    @GetMapping("/pathVariable/{name}")
    public String pathVariableApi(@PathVariable String name) {
    	return String.format("Getting detials for %s", name);
    }
}
