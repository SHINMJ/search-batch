package com.avatar.search.config;

import com.avatar.search.client.DataClient;
import com.avatar.search.domain.hotel.*;
import com.avatar.search.dto.ClientDataItemResponse;
import com.avatar.search.dto.ClientDataRequest;
import com.avatar.search.dto.ClientDataResponse;
import com.avatar.search.dto.HotelDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@SpringBatchTest
public class BatchTests {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private JpaCursorItemReader<HotelDTO> itemReader;

    @Autowired
    private EntityManager em;

    @Autowired
    private HotelRepository hotelRepository;

    @MockBean
    DataClient dataClient;

    @AfterEach
    void tearDown() {
        jobRepositoryTestUtils.removeJobExecutions();
        hotelRepository.deleteAllInBatch();
        hotelRepository.flush();
    }

    @Test
    void itemReader_호텔한건씩조회_success() throws Exception {
        //given
        for (int i = 0; i < 10; i++) {
            hotelRepository.save(
                    Hotel.builder()
                            .name("hotel"+i)
                            .address("address_"+i)
                            .area(Area.builder()
                                    .name("area_name")
                                    .code(UUID.randomUUID().toString())
                                    .build())
                            .classification(Classify.FAMILY)
                            .rooms(i+1)
                            .grade(Grade.FIVE)
                            .build()
            );
        }

        //when
        itemReader.open(new ExecutionContext());

        //then
        for (int i = 0; i < 10; i++) {
            assertThat(itemReader.read().name()).isEqualTo("hotel"+i);
        }

        assertNull(itemReader.read());
    }

    @Test
    void batch_success() throws Exception {
        //given
        for (int i = 0; i < 2; i++) {
            hotelRepository.save(
                    Hotel.builder()
                            .name("hotel"+i)
                            .address("address_"+i)
                            .area(Area.builder()
                                    .name("area_name")
                                    .code(UUID.randomUUID().toString())
                                    .build())
                            .classification(Classify.FAMILY)
                            .rooms(i+1)
                            .grade(Grade.FIVE)
                            .build()
            );
        }

        Mockito.when(dataClient.getData(new ClientDataRequest("hotel0")))
                .thenReturn(new ClientDataResponse("Mon, 29 Jan 2024 11:29:44 +0900", 10, 1, 100, List.of(new ClientDataItemResponse("신라호텔", "https://search.shopping.naver.com/gate.nhn?id=31282947756", "https://shopping-phinf.pstatic.net/main_3128294/31282947756.jpg", "170000", "1000000", "옥션", "1", "2"))));
        Mockito.when(dataClient.getData(new ClientDataRequest("hotel1")))
                .thenReturn(new ClientDataResponse("Mon, 29 Jan 2024 11:29:44 +0900", 10, 1, 100, List.of(new ClientDataItemResponse("신라호텔", "https://search.shopping.naver.com/gate.nhn?id=31282947756", "https://shopping-phinf.pstatic.net/main_3128294/31282947756.jpg", "170000", "1000000", "옥션", "2", "2"))));

        JobParametersBuilder job = new JobParametersBuilder().addString("job", "1");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(job.toJobParameters());

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
