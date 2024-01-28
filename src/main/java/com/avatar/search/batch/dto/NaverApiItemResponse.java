package com.avatar.search.batch.dto;

import org.springframework.util.StringUtils;

public record NaverApiItemResponse(String title, String link, String image,
                                   String lprice, String hprice, String mallName,
                                   String productId, String productType, String category1,
                                   String category2, String category3, String category4) {

    public boolean valid(){
        if (!StringUtils.hasText(title) || !StringUtils.hasText(link) || !StringUtils.hasText(image)
        || !StringUtils.hasText(lprice) || !StringUtils.hasText(hprice) || !StringUtils.hasText(mallName)
        || !StringUtils.hasText(productId) || !StringUtils.hasText(productType)){
            return false;
        }
        return true;
    }
}
