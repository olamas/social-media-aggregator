package com.olamas.socialmedia.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EntityScan({"com.olamas.socialmedia.twitter","com.olamas.socialmedia.aggregator"})
public class SocialMediaAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialMediaAggregatorApplication.class, args);
	}
}
