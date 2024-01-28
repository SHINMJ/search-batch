package com.avatar.search.batch.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void createProduct_success() {
        repository.save(Product.builder()
                .productId("1")
                .productType("2")
                .highPrice(1000L)
                .lowPrice(100L)
                .title("hotel buffet")
                .hotelId(1L)
                .build()
        );

        repository.flush();
    }
}