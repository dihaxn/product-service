package com.LittleLanka.product_service.dto.response;

import com.LittleLanka.product_service.dto.request.RequestProductListDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUpdateStockDTO {
    private Long outletId;
    private List<RequestProductListDTO> productList;
}
