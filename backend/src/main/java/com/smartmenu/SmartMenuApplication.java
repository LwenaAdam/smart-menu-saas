package com.smartmenu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Application Entry Point
 * 
 * @SpringBootApplication is a meta-annotation that combines:
 *  - @Configuration: Marks this class as a source of bean definitions
 *  - @EnableAutoConfiguration: Tells Spring Boot to auto-configure based on dependencies
 *  - @ComponentScan: Scans for @Component, @Service, @Controller, etc.
 */
@SpringBootApplication
@EnableJpaAuditing 
public class SmartMenuApplication {

   
    public static void main(String[] args) {
        SpringApplication.run(SmartMenuApplication.class, args);
        System.out.println("\n" +
            "===========================================\n" +
            "✅ SmartMenu Backend Started Successfully!\n" +
            "📡 API: http://localhost:8080\n" +
            "📖 Swagger UI: http://localhost:8080/swagger-ui.html\n" +
            "🗄️  PostgreSQL: localhost:5432/smartmenu\n" + // Updated to reflect Postgres
            "===========================================\n");
    }
}
