package com.justinswork.Corona.Tracking.Model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.justinswork.Corona.Tracking.Service.CovidService;

@Configuration
public class BeanConfiguration {

	@Bean
	public RestTemplate getRestTemplateBean() {
		return new RestTemplate();
	}
	

}
