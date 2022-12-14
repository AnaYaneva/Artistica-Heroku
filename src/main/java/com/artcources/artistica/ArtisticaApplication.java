package com.artcources.artistica;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class ArtisticaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtisticaApplication.class, args);
    }

}
