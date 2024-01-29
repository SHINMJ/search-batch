package com.avatar.search.infrastructure.dto;

import com.avatar.search.dto.ClientDataItemResponse;

public record NaverApiItemResponse(String title, String link, String image,
                                   String lprice, String hprice, String mallName,
                                   String productId, String productType, String category1,
                                   String category2, String category3, String category4) {

    public ClientDataItemResponse convertDTO(){
        return new ClientDataItemResponse(title, link, image, lprice, hprice, mallName, productId, productType);
    }
}
