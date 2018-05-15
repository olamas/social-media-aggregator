package com.olamas.socialmedia.stream.api;

import com.olamas.socialmedia.stream.api.twitter.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableAsync
@ComponentScan({"com.olamas.socialmedia.twitter","com.olamas.socialmedia.stream.api"})
@EntityScan({"com.olamas.socialmedia.twitter","com.olamas.socialmedia.stream.api"})
public class SocialMediaStreamApplication implements CommandLineRunner{

	private static final Logger LOGGER = LoggerFactory.getLogger(SocialMediaStreamApplication.class);

	private ExecutorService executorService;

	@Autowired
	private TwitterService twitterService;

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(SocialMediaStreamApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args == null || args.length == 0) {
			LOGGER.error("Value is expected as the command line parameter");
			return;
		}

		if (args[0].equals("start")) {
			executorService = Executors.newSingleThreadExecutor();
			executorService.execute(twitterService);
		}
	}

	@PreDestroy
	public void close(){
		LOGGER.info("Stopping twitter service ...");
		twitterService.stop();
	}

}
