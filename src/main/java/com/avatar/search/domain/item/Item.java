package com.avatar.search.domain.item;

import com.avatar.search.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String itemId;
    private String itemType;
    private String title;
    private String link;
    private String image;
    private Long lowPrice;
    private Long highPrice;
    private String mallName;

    @Column(nullable = false)
    private Long hotelId;

}
