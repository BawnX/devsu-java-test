package com.hexagonal.client.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    @Value("${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/postgres}")
    private String databaseUrl;

    @Value("${SPRING_DATASOURCE_USERNAME:postgres}")
    private String userName;

    @Value("${SPRING_DATASOURCE_PASSWORD:estanoesunaclave}")
    private String password;

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
