package com.LittleLanka.product_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponsePriceUpdateDTO {
    private Long priceUpdateId;
    private Long productId;
    private String productName;
    private Date priceUpdateDate;
    private Double price;
    private boolean priceUpdateStatus;
}
