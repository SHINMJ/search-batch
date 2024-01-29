package com.avatar.search.application;

import com.avatar.search.application.ProductService;
import com.avatar.search.domain.item.Item;
import com.avatar.search.domain.item.ItemRepository;
import com.avatar.search.dto.BatchItemDTO;
import com.avatar.search.dto.ClientDataItemResponse;
import com.avatar.search.dto.HotelDTO;
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
class ItemServiceTest {

    @Mock
    private ItemRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void storeItem_success() {

        //given
        HotelDTO hotel1 = new HotelDTO(1L, "hotel1");
        List<ClientDataItemResponse> list  = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new ClientDataItemResponse("title"+i, "link", "image",
                    "100", "10000", "naverpay", "1", "2"));
        }

        Mockito.when(repository.saveAll(anyList()))
                .thenReturn(list.stream()
                        .map(response -> Item.builder()
                                .hotelId(hotel1.id())
                                .itemId(response.itemId())
                                .itemType(response.itemType())
                                .title(response.title())
                                .image(response.image())
                                .link(response.link())
                                .highPrice(Long.valueOf(response.highPrice()))
                                .lowPrice(Long.valueOf(response.lowPrice()))
                                .mallName(response.mallName())
                                .build())
                        .collect(Collectors.toList()));


        //when
        List<Item> storeItem = service.storeItem(new BatchItemDTO(hotel1, list));

        //then
        assertThat(storeItem.size()).isEqualTo(2);
    }
}