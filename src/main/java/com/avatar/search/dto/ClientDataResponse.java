package com.avatar.search.dto;

import java.util.List;

public record ClientDataResponse (String lastDate, long total, int page, int display, List<ClientDataItemResponse> items){
}
