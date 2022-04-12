package com.apps.redditclonebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditclonebackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditclonebackendApplication.class, args);
    }

}
