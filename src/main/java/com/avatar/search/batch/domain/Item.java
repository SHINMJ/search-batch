package com.avatar.search.batch.domain;

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
//@Table(name = "items", uniqueConstraints = {
//        @UniqueConstraint(
//                name = "PRODUCT_UNIQUE",
//                columnNames = {"product_id, product_type"}
//        )
//})
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_id")
    private String productId;
    @Column(name = "product_type")
    private String productType;
    private String title;
    private String link;
    private String image;
    private Long lowPrice;
    private Long highPrice;
    private String mallName;

}
