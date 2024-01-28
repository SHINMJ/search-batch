package com.avatar.search.batch.client;

import com.avatar.search.batch.client.DataClient;
import com.avatar.search.batch.dto.NaverApiRequest;
import com.avatar.search.batch.dto.NaverApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DataClientTest {
    @Autowired
    private DataClient client;

    @Test
    void getData_success() {
        NaverApiResponse response = client.getData(new NaverApiRequest("호텔뷔페"));

        assertThat(response.display()).isEqualTo(10);
        System.out.println(response);
    }
}