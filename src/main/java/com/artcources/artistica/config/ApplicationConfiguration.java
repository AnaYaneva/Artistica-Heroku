package com.artcources.artistica.config;
import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.Map;

@Configuration
public class ApplicationConfiguration {
    private final CloudinaryConfig cloudinaryConfig;

    public ApplicationConfiguration(CloudinaryConfig cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }



    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                Map.of(
                        "cloud_name",cloudinaryConfig.getCloudName(),
                        "api_key",cloudinaryConfig.getApiKey(),
                        "api_secret",cloudinaryConfig.getApiSecret()
                )
        );

    }
}