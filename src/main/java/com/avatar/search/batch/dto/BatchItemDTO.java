package com.avatar.search.batch.dto;

import java.util.List;

public record BatchItemDTO(HotelDTO hotel,
                           List<NaverApiItemResponse> responses) {
}
