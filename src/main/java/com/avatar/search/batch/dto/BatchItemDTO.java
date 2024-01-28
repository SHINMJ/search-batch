package com.avatar.search.batch.dto;

import com.avatar.search.hotel.dto.HotelDTO;

import java.util.List;

public record BatchItemDTO(HotelDTO hotel,
                           List<NaverApiItemResponse> responses) {
}
