package com.example.restservice.config;

import com.example.restservice.client.MultiplicationClient;
import com.example.restservice.domain.Multiplication;
import com.example.restservice.service.MultiplcationService;
import com.example.restservice.service.MultiplicationServiceImpl;
import com.example.restservice.service.RandomGeneratorService;
import com.example.restservice.service.RandomGeneratorServiceImpl;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
@Profile(value = "unit-test")
public class UnitTestConfig {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private MultiplicationClient bookClient = createClient(MultiplicationClient.class, "http://localhost:8080/multiplications/random");

    private static <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(type))
                .logLevel(Logger.Level.FULL)
                .target(type, uri);
    }
}
