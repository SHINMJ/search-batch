package com.avatar.search.batch.application;

import com.avatar.search.batch.domain.Product;
import com.avatar.search.batch.domain.ProductRepository;
import com.avatar.search.batch.dto.BatchItemDTO;
import com.avatar.search.batch.dto.NaverApiItemResponse;
import com.avatar.search.batch.dto.HotelDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void storeItem_success() {

        //given
        HotelDTO hotel1 = new HotelDTO(1L, "hotel1");
        List<NaverApiItemResponse> list  = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new NaverApiItemResponse("title"+i, "link", "image",
                    "100", "10000", "naverpay", "1", "2", "category1", "category2", "category3", "category4"));
        }

        Mockito.when(repository.saveAll(anyList()))
                .thenReturn(list.stream()
                        .map(response -> Product.builder()
                                .hotelId(hotel1.id())
                                .productId(response.productId())
                                .productType(response.productType())
                                .title(response.title())
                                .image(response.image())
                                .link(response.link())
                                .highPrice(Long.valueOf(response.hprice()))
                                .lowPrice(Long.valueOf(response.lprice()))
                                .mallName(response.mallName())
                                .build())
                        .collect(Collectors.toList()));


        //when
        List<Product> storeItem = service.storeItem(new BatchItemDTO(hotel1, list));

        //then
        assertThat(storeItem.size()).isEqualTo(2);
    }
}