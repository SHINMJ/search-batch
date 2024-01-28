package com.avatar.search.batch.config;

import com.avatar.search.batch.application.ItemService;
import com.avatar.search.batch.client.DataClient;
import com.avatar.search.batch.dto.BatchItemDTO;
import com.avatar.search.batch.dto.NaverApiItemResponse;
import com.avatar.search.batch.dto.NaverApiRequest;
import com.avatar.search.batch.dto.NaverApiResponse;
import com.avatar.search.hotel.dto.HotelDTO;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchConfig {

    private final DataClient client;
    private final ItemService itemService;
    private final EntityManagerFactory entityManagerFactory;


    @Value("${batch.chunk}")
    private int chunkSize;

    @Bean
    public Job job(JobRepository jobRepository, Step step){
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end().build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("shop", jobRepository)
                .chunk(chunkSize, transactionManager)
                .reader(itemReader())
                .processor(item -> process((HotelDTO) item))
                .writer(chunk -> {
                    log.info("chunk : " + chunk.getItems().size());

                    chunk.getItems()
                            .forEach(items -> {
                                itemService.storeItem((BatchItemDTO) items);
                            });
                })
                .build();

    }

    @Bean
    @StepScope
    public JpaCursorItemReader<HotelDTO> itemReader(){
        return new JpaCursorItemReaderBuilder<HotelDTO>()
                .name("jpaCursorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT new com.avatar.search.hotel.dto.HotelDTO(h.id, h.name) FROM Hotel h")
                .build();
    }

    private BatchItemDTO process(HotelDTO  item){
        List<NaverApiItemResponse> responses = new ArrayList<>();
        int start = 1;
        String query = item.name() + "뷔페";

        while (true) {

            NaverApiResponse data = client.getData(new NaverApiRequest(query, start));
            responses.addAll(data.items());

            start += data.display();

            if (start > 1_000 || start > data.total()){
                return new BatchItemDTO(item, responses);
            }
        }
    }

}
