package com.portfolio.analyzer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI portfolioAnalyzerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Portfolio Analyzer API")
                        .description("Multi-Agent AI Portfolio Analyzer & Optimizer")
                        .version("1.0.0")
                        .contact(new Contact().name("Portfolio AI Team")));
    }
}
