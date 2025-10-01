package com.arnavgautam.taskmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for Swagger documentation
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Task Manager API",
                version = "1.0.0",
                description = "A production-ready REST API for task management with JWT authentication",
                contact = @Contact(
                        name = "Arnav Gautam",
                        email = "arnavgautam0209@gmail.com",
                        url = "https://github.com/arnavgautam0209"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Development server"),
                @Server(url = "https://task-manager-api.herokuapp.com", description = "Production server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
