package com.LittleLanka.product_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseStockDto {
    private Long productId;
    private String productName;
    private double stockQuantity;
}
