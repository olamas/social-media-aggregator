package com.olamas.socialmedia.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class SocialMediaAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaAggregatorApplication.class, args);
	}
}
