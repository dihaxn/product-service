package com.LittleLanka.product_service.dto.response;

import com.LittleLanka.product_service.entity.enums.CatagoryType;
import com.LittleLanka.product_service.entity.enums.MeasuringUnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseGetAllProductsDTO {
    private Long productId;
    private String productName;
    private CatagoryType productCatagory;//SHORTIES, SWEET, ITEMS, CAKES, BREADS, SNACKS , OTHER
    private MeasuringUnitType productMeasuringUnitType = MeasuringUnitType.NUMBER;//NUMBER, KG, PACKET
    private String imageUrl;
    private Double price;
    private boolean productStatus;

}
