package com.LittleLanka.product_service.repository;

import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.entity.enums.CatagoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductCatagoryAndProductName(CatagoryType productCatagory, String productName);

    List<Product> findAllByProductStatusEquals(boolean status);

    @Query(value = "SELECT * FROM product WHERE (LOWER(product_name) LIKE CONCAT('%', LOWER(?1), '%') OR LOWER(product_name) " +
            "LIKE CONCAT('_', LOWER(?1), '%')) AND product_status = 1 ORDER BY CASE WHEN LOWER(product_name) " +
            "LIKE CONCAT('%', LOWER(?1), '%') THEN 1 WHEN LOWER(product_name) LIKE CONCAT('_', LOWER(?1), '%') THEN 2 ELSE 3 " +
            "END, product_name", nativeQuery = true)
    List<Product> findByProductNameContainingIgnoreCase(String productName);
}
