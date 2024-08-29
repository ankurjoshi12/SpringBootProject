package com.bb.AutomationUtils.RestController;

import com.bb.AutomationUtils.Interfaces.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CricketCoachRestController {
    private Coach mycoach ;

    @Autowired
    public void CoachRestController(@Qualifier("footballCoach") Coach coach){
        System.out.println(getClass().getSimpleName());
        mycoach = coach ;
    }
    @GetMapping("/coach")
    public String getCricketCoach(){
        return mycoach.getCoach();
    }
}
