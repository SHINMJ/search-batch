package com.avatar.search.domain.hotel;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Grade {
    ONE(1, "1성급"),
    TWO(2, "2성급"),
    THREE(3, "3성급"),
    FOUR(4, "4성급"),
    FIVE(5, "5성급"),
    UNKNOWN(6, "정해지지 않음");

    private final int code;
    private final String name;
}
