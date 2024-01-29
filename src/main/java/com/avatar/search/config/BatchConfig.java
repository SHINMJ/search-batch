package com.avatar.search.config;

import com.avatar.search.application.ProductService;
import com.avatar.search.client.DataClient;
import com.avatar.search.dto.*;
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
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchConfig {

    private final DataClient client;
    private final ProductService productService;
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
                                productService.storeItem((BatchItemDTO) items);
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
                .queryString("SELECT new com.avatar.search.dto.HotelDTO(h.id, h.name) FROM Hotel h")
                .build();
    }

    private BatchItemDTO process(HotelDTO  item){
        List<ClientDataItemResponse> responses = new ArrayList<>();
        int start = 1;
        String query = item.name() + "뷔페";

        while (true) {

            ClientDataResponse data = client.getData(new ClientDataRequest(query, start));
            if (data == null){
                return new BatchItemDTO(item, responses);
            }
            responses.addAll(data.items());
            start += data.display();

            if (start > 1_000 || start > data.total()){
                return new BatchItemDTO(item, responses);
            }
        }
    }

}
