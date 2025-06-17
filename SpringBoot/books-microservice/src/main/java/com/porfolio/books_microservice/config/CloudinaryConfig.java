package com.porfolio.books_microservice.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {
     private final CloudinaryProperties cloudinaryProperties;

    public CloudinaryConfig(CloudinaryProperties cloudinaryProperties) {
        this.cloudinaryProperties = cloudinaryProperties;
        System.out.println("CLOUD_NAME: " + cloudinaryProperties.getCloudName());
        System.out.println("API_KEY: " + cloudinaryProperties.getApiKey());
        System.out.println("API_SECRET: " + cloudinaryProperties.getApiSecret());
    }

    @Bean
    public Cloudinary cloudinary() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudinaryProperties.getCloudName());
        config.put("api_key", cloudinaryProperties.getApiKey());
        config.put("api_secret", cloudinaryProperties.getApiSecret());

        config.put("uploader_strategy_class", "com.cloudinary.http44.UploaderStrategy");

        return new Cloudinary(config);
    }
}
