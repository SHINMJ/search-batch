package com.avatar.search.infrastructure.dto;

import com.avatar.search.dto.ClientDataRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Objects;

public record NaverApiRequest(String query, int display, int start, String sort,
                              String filter, String exclude) {

    public NaverApiRequest(String query) {
        this(query, 10, 1, NaverApiSort.sim.getCode(), null, null);
        Objects.requireNonNull(query);
    }

    public NaverApiRequest(String query, int start) {
        this(query, 100, start, NaverApiSort.sim.getCode(), null, null);
        Objects.requireNonNull(query);
    }

    public NaverApiRequest(String query, int start, int display) {
        this(query, display, start, NaverApiSort.sim.getCode(), null, null);
        Objects.requireNonNull(query);
    }

    public static NaverApiRequest of(ClientDataRequest request) {
        return new NaverApiRequest(request.query(), request.page(), request.display());
    }

    public MultiValueMap<String, String> convertQueryParams() {
        ObjectMapper objectMapper = new ObjectMapper();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        Map<String, String> map = objectMapper.convertValue(this, new TypeReference<>() {});

        params.setAll(map);

        return params;
    }
}
