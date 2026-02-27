package br.com.ffagundes.restapisample.application.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("RESTful API with kotlin 1.9.25 and Spring Boot 3.5.9")
                    .version("v1")
                    .description("API RESTful for tests")
                    .termsOfService("https://google.com")
                    .license(
                        License().name("Apache 2.0")
                            .url("https://tomcat.apache.org/")
                    )
            )

    }
}