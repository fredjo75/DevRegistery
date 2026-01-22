package com.fredjo.DevRegistery.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for ModelMapper bean.
 * Enables dependency injection of ModelMapper throughout the application.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Creates and configures the ModelMapper bean.
     *
     * @return configured ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Configure ModelMapper properties if needed
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)  // Skip null values during mapping
                .setAmbiguityIgnored(true); // Ignore ambiguous mappings
        return modelMapper;
    }
}
