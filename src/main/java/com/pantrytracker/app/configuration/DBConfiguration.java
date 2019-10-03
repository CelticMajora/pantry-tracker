package com.pantrytracker.app.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfiguration {
	
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
	@Profile("development")
	@Bean
	public void devDatabaseConnection() {
		
	}
	
	@Profile("production")
	@Bean
	public void prodDatabaseConnection() {
		
	}

}
