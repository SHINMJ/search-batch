package com.avatar.search.infrastructure;

import com.avatar.search.client.DataClient;
import com.avatar.search.dto.ClientDataRequest;
import com.avatar.search.dto.ClientDataResponse;
import com.avatar.search.infrastructure.dto.NaverApiRequest;
import com.avatar.search.infrastructure.dto.NaverApiResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NaverSearchApiClient implements DataClient {
    private static final String PATH = "/shop.json";

    @Value("${naver-api.base-url}")
    private String baseUrl;
    @Value("${naver-api.client-id}")
    private String clientId;
    @Value("${naver-api.client-secret}")
    private String clientSecret;
    private WebClient webClient;

    @PostConstruct
    public void init() {
         webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                .build();
    }

    @Override
    public ClientDataResponse getData(ClientDataRequest request) {
        NaverApiResponse naverApiResponse = getSearchApiData(NaverApiRequest.of(request));

        return naverApiResponse.convertClientDataResponseDTO();
    }

    private NaverApiResponse getSearchApiData(NaverApiRequest request){
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(PATH)
                        .queryParams(request.convertQueryParams())
                        .build()
                )
                .retrieve()
                .bodyToMono(NaverApiResponse.class)
                .block();
    }
}
