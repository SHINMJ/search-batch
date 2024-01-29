package com.avatar.search.domain.hotel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Classify {
    TOURIST("tourist", "관광호텔업"),
    FAMILY("family", "가족호텔업"),
    TRADITIONAL("traditional", "한국전통호텔업"),
    SMALL("small", "소형호텔업");

    private final String code;
    private final String name;
}
