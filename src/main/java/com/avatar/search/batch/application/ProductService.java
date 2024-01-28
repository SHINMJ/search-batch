package com.avatar.search.batch.application;

import com.avatar.search.batch.domain.Product;
import com.avatar.search.batch.domain.ProductRepository;
import com.avatar.search.batch.dto.BatchItemDTO;
import com.avatar.search.batch.dto.NaverApiItemResponse;
import jakarta.validation.Valid;
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

    private final ProductRepository repository;

    public List<Product> storeItem(BatchItemDTO itemDTO) {
        log.info("----- store item " + itemDTO.hotel());
        log.info("----- store item " + itemDTO.responses().size());
        List<NaverApiItemResponse> responses = itemDTO.responses();
        Long hotelId = itemDTO.hotel().id();
        List<Product> products = responses.stream()
                .filter(NaverApiItemResponse::valid)
                .map(response -> Product.builder()
                        .hotelId(hotelId)
                        .productId(response.productId())
                        .productType(response.productType())
                        .title(response.title())
                        .image(response.image())
                        .link(response.link())
                        .highPrice(Long.parseLong(response.hprice()))
                        .lowPrice(Long.parseLong(response.lprice()))
                        .mallName(response.mallName())
                        .build())
                .collect(Collectors.toList());

        List<Product> saved = repository.saveAll(products);
        return saved;
    }
}
