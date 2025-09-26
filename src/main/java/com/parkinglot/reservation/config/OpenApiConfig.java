package com.parkinglot.reservation.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI parkingOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Parking Lot Reservation API")
                        .version("v1.0")
                        .description("Backend API for managing floors, slots, and reservations with validation, conflict prevention, and fee calculation.")
        );
    }
}