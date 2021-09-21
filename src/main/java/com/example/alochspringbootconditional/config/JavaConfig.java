package com.example.alochspringbootconditional.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import profile.DevProfile;
import profile.ProductionProfile;
import profile.SystemProfile;

@Configuration
public class JavaConfig {

    @Bean
    @ConditionalOnProperty(
            value="alochspringbootconditional.profile.dev",
            havingValue = "true"
    )
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean
    @ConditionalOnProperty(
            value="alochspringbootconditional.profile.dev",
            havingValue = "false"
    )
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}
