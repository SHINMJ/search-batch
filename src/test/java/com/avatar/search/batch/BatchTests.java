package com.avatar.search.batch;

import com.avatar.search.hotel.domain.*;
import com.avatar.search.batch.dto.HotelDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        JobParametersBuilder job = new JobParametersBuilder().addString("job", "1");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(job.toJobParameters());

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}
