package org.example.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .title("X标题")
            .description("X描述")
            .version("v1")
            .license(new License().name("Xpache 2.0").url("xxx"));
        ExternalDocumentation externalDocs = new ExternalDocumentation()
            .description("X外部文档")
            .url("xxx");
        return new OpenAPI()
            .info(info)
            .externalDocs(externalDocs);
    }
}
