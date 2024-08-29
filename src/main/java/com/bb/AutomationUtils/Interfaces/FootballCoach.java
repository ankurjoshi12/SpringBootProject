package com.bb.AutomationUtils.Interfaces;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FootballCoach implements Coach{

    public void FootballCoach(){
        System.out.println("In Constructor -"+getClass().getSimpleName());
    }
    @Override
    public String getCoach() {
        System.out.println(getClass().getSimpleName());
        return " This is football coach , work on endurance !!! ";
    }
}
