package com.avatar.search.client;

import com.avatar.search.dto.ClientDataRequest;
import com.avatar.search.dto.ClientDataResponse;

public interface DataClient {
    ClientDataResponse getData(ClientDataRequest request);
}
