package com.avatar.search.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${naver-api.base-url}")
    private String baseUrl;
    @Value("${naver-api.client-id}")
    private String clientId;
    @Value("${naver-api.client-secret}")
    private String clientSecret;

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader( "X-Naver-Client-Secret", clientSecret)
                .build();
    }
}
