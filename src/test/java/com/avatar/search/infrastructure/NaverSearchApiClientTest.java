package com.avatar.search.infrastructure;

import com.avatar.search.dto.ClientDataRequest;
import com.avatar.search.dto.ClientDataResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {NaverSearchApiClient.class})
class NaverSearchApiClientTest {

    @Autowired
    private NaverSearchApiClient client;

    @Test
    void getData_success() {
        ClientDataResponse response = client.getData(new ClientDataRequest("호텔뷔페", 1, 1));

        assertThat(response.display()).isEqualTo(1);
        System.out.println(response);
    }
}