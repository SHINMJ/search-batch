package com.avatar.search.hotel.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

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