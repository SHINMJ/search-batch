package com.avatar.search.batch.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NaverApiSort {
    sim("sim", "정확도순으로 내림차순"),
    date("date", "정확도순으로 내림차순"),
    asc("asc", "정확도순으로 내림차순"),
    dsc("dsc", "정확도순으로 내림차순");

    private final String code;
    private final String desc;

}
