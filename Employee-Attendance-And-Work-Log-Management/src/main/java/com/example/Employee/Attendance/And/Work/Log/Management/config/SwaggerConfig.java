package com.example.Employee.Attendance.And.Work.Log.Management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Employee Attendance & Work Log Management API",
                description = "Backend APIs for managing employee attendance, work logs, and approvals",
                version = "1.0.0",
                contact = @Contact(
                        name = "Backend Team",
                        email = "manojahavali@gmail.com"
                )
        )

)

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Employee Attendance & Work Log Management API")
                        .version("1.0.0"));
    }
}
