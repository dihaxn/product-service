package com.LittleLanka.product_service.dto.paginated;

import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginatedResponseGetAllProductsDTO {
    private List<ResponseGetAllProductsDTO> list;
    private long count;
}
