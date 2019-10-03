package com.pantrytracker.app.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.mail")
public class MailConfiguration {

	private String host;
	private String port;
	private String username;
	private String password;
	
	@Profile("development")
	@Bean
	public void devMailConnection() {
		
	}
	
	@Profile("production")
	@Bean
	public void prodMailConnection() {
		
	}
}
