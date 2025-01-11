package com.LittleLanka.product_service.repository;

import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.entity.enums.CatagoryType;
import org.springframework.data.domain.Sort;
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

    List<Product> findByProductNameContainingIgnoreCaseAndProductStatusIsTrue(String productName, Sort sort);
}
