package com.LittleLanka.product_service.dto.request;

import com.LittleLanka.product_service.entity.enums.CatagoryType;
import com.LittleLanka.product_service.entity.enums.MeasuringUnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestUpdateProductDTO {
    private String productName;
    private CatagoryType productCatagory;
    private MeasuringUnitType productMeasuringUnitType;
    private MultipartFile ImageFile;
    private Double price;
    private boolean productStatus;
    private String date;

}
