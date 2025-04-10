package com.LittleLanka.product_service.entity;

import com.LittleLanka.product_service.entity.enums.CatagoryType;
import com.LittleLanka.product_service.entity.enums.MeasuringUnitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", length = 100)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_catagory")
    private CatagoryType productCatagory; // SHORTIES, BISCUITS, CAKES, BREADS, OTHER

    @Column(name = "product_status", columnDefinition = "TINYINT default 0") // 1=active, 0=inactive
    private boolean productStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "measuring_type")
    private MeasuringUnitType productMeasuringUnitType = MeasuringUnitType.NUMBER;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product")
    private List<PriceUpdate> productUpdates;

    @OneToMany(mappedBy = "product")
    private List<Stock> stocks;

}
