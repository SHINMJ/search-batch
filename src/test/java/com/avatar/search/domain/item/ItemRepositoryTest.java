package com.avatar.search.domain.item;

import com.avatar.search.domain.item.Item;
import com.avatar.search.domain.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    @Test
    void createProduct_success() {
        repository.save(Item.builder()
                .itemId("1")
                .itemType("2")
                .highPrice(1000L)
                .lowPrice(100L)
                .title("hotel buffet")
                .hotelId(1L)
                .build()
        );

        repository.flush();
    }
}