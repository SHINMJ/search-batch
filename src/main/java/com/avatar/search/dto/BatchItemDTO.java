package com.avatar.search.dto;

import java.util.List;

public record BatchItemDTO(HotelDTO hotel,
                           List<ClientDataItemResponse> responses) {
}
