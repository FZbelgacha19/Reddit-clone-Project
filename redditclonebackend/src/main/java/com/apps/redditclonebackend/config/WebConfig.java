package com.apps.redditclonebackend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry // cros = cross origin resource sharing (fr: accès à un ressource depuis un autre domaine)
                .addMapping("/**") // mean any URL (i.e. any HTTP request)
                .allowedOriginPatterns("*") // mean that any origin is allowed (i.e. any domain)
                .allowedMethods("*") // Mean that any HTTP method is allowed (i.e. GET, POST, PUT, DELETE, etc.)
                .maxAge(3600L) // mean that the CORS policy will be valid for 1 hour (i.e after 1 hour, the CORS policy will be invalid)
                .allowedHeaders("*") // mean that any header is allowed (i.e. "Authorization", "Content-Type", etc.)
                .exposedHeaders("Authorization") // mean that the Authorization header will be exposed to the client (i.e. the client will be able to see the Authorization header)
                .allowCredentials(true); // mean that the CORS policy will allow credentials (i.e. cookies, authorization headers, etc.)
    }
}
