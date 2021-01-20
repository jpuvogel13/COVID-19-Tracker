package com.justinswork.Corona.Tracking;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import GUI.UserInterface;



@SpringBootApplication
public class CoronaTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronaTrackingApplication.class, args);
		
		System.setProperty("java.awt.headless", "false");
		
		UserInterface ui = new UserInterface();
		ui.run();
	}

}
