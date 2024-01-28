package com.avatar.search.batch.client;

import com.avatar.search.batch.dto.NaverApiRequest;
import com.avatar.search.batch.dto.NaverApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class DataClient {
    private static final String PATH = "/shop.json";

    private final WebClient webClient;

    public NaverApiResponse getData(NaverApiRequest request){
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
