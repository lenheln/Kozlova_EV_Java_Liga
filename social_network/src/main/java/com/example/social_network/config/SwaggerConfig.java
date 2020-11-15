package com.example.social_network.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Класс конфигурации swagger
 */
@org.springframework.context.annotation.Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Конфигурация swagger
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Social network API",
                "Передача сообщений между API и пользователем осуществляется через протокол HTTP. " +
                        "Запросы с параметрами передаются через HTTP методы: GET, POST, PUT, DELETE, PATCH. ",
                "",
                "",
                new Contact("Козлова Елена", "", "lenhelncode@gmail.com"),
                "", "", Collections.emptyList());
    }
}
