package com.avatar.search.batch.dto;

import java.util.List;

public record NaverApiResponse(String lastBuildDate, long total, int start, int display, List<NaverApiItemResponse> items) {
}
