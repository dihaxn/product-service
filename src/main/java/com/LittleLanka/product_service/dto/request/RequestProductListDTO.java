package com.LittleLanka.product_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestProductListDTO {
    private Long productId;
    private double stockQuantity;
}
