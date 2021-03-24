package ru.murashkin.ladaAps.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfig {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Profile("dev")
    @Bean
    public String devDataBaseConnection() {
        return "DEV DB";
    }

    @Profile("test")
    @Bean
    public String testDataBaseConnection() {
        return "TEST DB";
    }
}
