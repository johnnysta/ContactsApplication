package com.example.contactsapplication.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {

//        SpringDocUtils.getConfig().replaceWithClass(org.springdoc.core.GroupedOpenApi.class, GroupedOpenApi.class);

        return new OpenAPI()
                .info(new Info()
                        .title("API for Contacts Application")
                        .version("0.0.1")
                        .description("This is the API for Contacts Application."))
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                                .description("Basic authentication")))
                .addSecurityItem(
                        new SecurityRequirement().addList("basicScheme", Arrays.asList("read", "write")));

    }

}
