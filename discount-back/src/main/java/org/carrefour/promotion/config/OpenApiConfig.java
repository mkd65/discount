package org.carrefour.promotion.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI discountApi() {
        return new OpenAPI()
                .info(new Info()
                              .title("Carrefour Discount Kata API")
                              .version("1.0.0")
                              .description("API for applying discount codes to shopping carts"));
    }
}
