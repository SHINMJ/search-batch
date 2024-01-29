package com.avatar.search.infrastructure.dto;

import com.avatar.search.dto.ClientDataResponse;

import java.util.List;

public record NaverApiResponse(String lastBuildDate, long total, int start, int display, List<NaverApiItemResponse> items) {

    public ClientDataResponse convertClientDataResponseDTO() {

        return new ClientDataResponse(this.lastBuildDate, this.total, this.start, this.display,
                items.stream()
                        .map(NaverApiItemResponse::convertDTO)
                        .toList());
    }
}
