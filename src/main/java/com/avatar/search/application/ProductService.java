package com.avatar.search.application;

import com.avatar.search.domain.item.Item;
import com.avatar.search.domain.item.ItemRepository;
import com.avatar.search.dto.BatchItemDTO;
import com.avatar.search.dto.ClientDataItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ItemRepository repository;

    public List<Item> storeItem(BatchItemDTO itemDTO) {
        log.info("----- store item " + itemDTO.hotel());
        log.info("----- store item " + itemDTO.responses().size());
        List<ClientDataItemResponse> responses = itemDTO.responses();
        Long hotelId = itemDTO.hotel().id();
        List<Item> items = responses.stream()
                .filter(ClientDataItemResponse::valid)
                .map(response -> Item.builder()
                        .hotelId(hotelId)
                        .itemId(response.itemId())
                        .itemType(response.itemType())
                        .title(response.title())
                        .image(response.image())
                        .link(response.link())
                        .highPrice(Long.parseLong(response.highPrice()))
                        .lowPrice(Long.parseLong(response.lowPrice()))
                        .mallName(response.mallName())
                        .build())
                .collect(Collectors.toList());

        List<Item> saved = repository.saveAll(items);
        return saved;
    }
}
