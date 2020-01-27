package com.petersonv.eglucometer.userRegistrationService.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("redis")
public class RedisConfiguration {
    public String password;
    public String address;
}