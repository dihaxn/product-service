package com.LittleLanka.product_service.repository;

import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.entity.enums.CatagoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<Product> findAllByProductStatusEquals(boolean isActive);

    List<Product> getProductByProductId(Long productId);

    Page<Product> findAllByProductStatusEquals(boolean status, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndProductStatusIsTrue(String productName, Pageable pageable);

    Page<Product> findAllByProductCatagoryEquals(CatagoryType productCatagory, Pageable pageable);

}
