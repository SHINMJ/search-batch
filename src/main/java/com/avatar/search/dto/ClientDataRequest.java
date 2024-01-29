package com.avatar.search.dto;

import java.util.Objects;

public record ClientDataRequest(String query, int page, int display) {
    private static final int DEFAULT_DISPLAY = 100;

    public ClientDataRequest(String query) {
        this(query, 1, DEFAULT_DISPLAY);
        Objects.requireNonNull(query);
    }

    public ClientDataRequest(String query, int page) {
        this(query, page, DEFAULT_DISPLAY);
        Objects.requireNonNull(query);
    }
}
