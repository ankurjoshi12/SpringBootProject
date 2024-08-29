package com.bb.AutomationUtils.userdetailsapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
(scanBasePackages = {"com/bb/AutomationUtils"})
public class UserDetailsAppApplication implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsAppApplication.class);

	public static void main(String[] args) {
//		SpringApplication.run(AutomationUtilsApplication.class, args);
		logger.info("################## 	serverName: " + System.getenv("serverName"));
		System.out.println("################## 	suiteName: " + System.getProperty("suiteName"));
		SpringApplication.run(UserDetailsAppApplication.class, args);
	}
	@Override
    public void run(String... args) throws Exception {
        String serverName = System.getProperty("serverName", "https://www.bigbasket.com");
        String suiteName = System.getProperty("suiteName", "defaultSuite");
        String xProject = System.getProperty("X-Project", "defaultProject");

        logger.info("Using serverName: " + serverName);
        logger.info("Using suiteName: " + suiteName);
        logger.info("Using X-Project: " + xProject);
    }
}