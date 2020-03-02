package org.mflix.forecast.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("application")
public class ApplicationProperties {
    private String doubanApiKey;
    // private String avatarPath;
}