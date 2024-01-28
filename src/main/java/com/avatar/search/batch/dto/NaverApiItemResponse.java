package com.avatar.search.batch.dto;

import com.avatar.search.hotel.dto.HotelDTO;

public record NaverApiItemResponse(String title, String link, String image,
                                   String lprice, String hprice, String mallName,
                                   String productId, String productType, String category1,
                                   String category2, String category3, String category4) {
}
