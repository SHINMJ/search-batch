package com.avatar.search.dto;

import org.springframework.util.StringUtils;

public record ClientDataItemResponse (
        String title, String link, String image,
        String lowPrice, String highPrice, String mallName,
        String itemId, String itemType
){


    public boolean valid(){
        if (!StringUtils.hasText(title) || !StringUtils.hasText(link) || !StringUtils.hasText(image)
                || !StringUtils.hasText(lowPrice) || !StringUtils.hasText(highPrice) || !StringUtils.hasText(mallName)
                || !StringUtils.hasText(itemId) || !StringUtils.hasText(itemType)){
            return false;
        }
        return true;
    }
}
