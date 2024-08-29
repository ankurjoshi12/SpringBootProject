package com.bb.AutomationUtils.Interfaces;

import org.springframework.stereotype.Component;

@Component
public class CricketCoach implements Coach{

    public void CricketCoach(){
        System.out.println("In Constructor -"+getClass().getSimpleName());
    }
    @Override
    public String getCoach() {
        return "This is cricket coach , practise everyday !!!";
    }
}
