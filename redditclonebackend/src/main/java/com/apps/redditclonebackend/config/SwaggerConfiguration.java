package com.apps.redditclonebackend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


    @Bean
    // Docket est une classe qui permet de configurer le Swagger
    // redditCloneApi est le nom de l'API
    public Docket redditCloneApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) // on sélectionne toutes les classes qui implémentent l'interface RequestHandler
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Reddit Clone API")
                .version("1.0")
                .description("API pour le projet Reddit Clone")
                .contact(new Contact("Fatimazahra Belgacha", "www.fatimazahra.com", "fatimazahra@gmail.com"))
                .license("Apache License Version 2.0")
                .build();
    }
}