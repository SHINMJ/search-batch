package com.avatar.search.domain.hotel;

import com.avatar.search.domain.hotel.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class HotelRepositoryTest {

    @Autowired
    private HotelRepository repository;

    @Test
    void create() {
        Hotel hotel = Hotel.builder()
                .name("hotel1")
                .grade(Grade.FIVE)
                .address("address")
                .classification(Classify.FAMILY)
                .rooms(10)
                .area(Area.builder().code("11").name("경기도").build())
                .build();

        repository.save(hotel);

        repository.flush();
    }
}