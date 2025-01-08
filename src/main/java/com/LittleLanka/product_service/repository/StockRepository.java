package com.LittleLanka.product_service.repository;

import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface StockRepository extends JpaRepository<Stock, Long> {
    Boolean existsByOutletIdAndProduct(Long outletId, Product product);

    List<Stock> getAllByOutletId(Long outletId);

    Stock findByOutletIdAndProduct(Long outletId, Product product);

}
