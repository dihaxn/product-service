package com.LittleLanka.product_service.dto.response;

import com.LittleLanka.product_service.entity.enums.CatagoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseInventryDto {
    private Long productId;
    private String productName;
    private double stockQuantity;
    private double price;
    private CatagoryType catagoryType;
    private String imageUrl;
}
