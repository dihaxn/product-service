package com.LittleLanka.product_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "stock")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "outlet_id")
    private Long outletId;

    @Column(name = "stock_quantity")
    private double stockQuantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
