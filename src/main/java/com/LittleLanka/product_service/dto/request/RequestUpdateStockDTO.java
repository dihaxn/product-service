package com.LittleLanka.product_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestUpdateStockDTO {
    private Long outletId;
    private boolean isIncrease;
    private List<RequestProductListDTO> productList;
}
