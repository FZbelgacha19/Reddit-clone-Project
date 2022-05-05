package com.apps.redditclonebackend;

import com.apps.redditclonebackend.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)

public class RedditclonebackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditclonebackendApplication.class, args);
    }

}
