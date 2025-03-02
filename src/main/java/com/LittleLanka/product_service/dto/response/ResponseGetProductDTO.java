package com.LittleLanka.product_service.dto.response;

import com.LittleLanka.product_service.entity.enums.CatagoryType;
import com.LittleLanka.product_service.entity.enums.MeasuringUnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseGetProductDTO {
    private String productName;
    private CatagoryType productCatagory;
    private MeasuringUnitType productMeasuringUnitType;
    private String imageUrl;
    private Double todayPrice;
    private boolean productStatus;
    private Double lastUpdatedPrice;
    private String lastUpdatedDate;
}
