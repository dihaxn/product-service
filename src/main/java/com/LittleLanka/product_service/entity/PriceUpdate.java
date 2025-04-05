package com.LittleLanka.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "price_update")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_update_id")
    private Long priceUpdateId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "update_date", columnDefinition = "DATE")
    private Date priceUpdateDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "price_update_status", columnDefinition = "TINYINT default 0") // 1=active, 0=inactive
    private boolean priceUpdateStatus;

}
