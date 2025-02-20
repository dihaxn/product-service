package com.LittleLanka.product_service.service;

import com.LittleLanka.product_service.dto.ProductDTO;
import com.LittleLanka.product_service.dto.paginated.PaginatedResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.request.*;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.entity.enums.CatagoryType;
import org.springframework.core.io.Resource;

import java.util.List;


public interface ProductService {
    ProductDTO saveProduct(RequestSaveProductDto requestSaveProductDTO);

    PaginatedResponseGetAllProductsDTO getAllProducts(int page, int size);

    PaginatedResponseGetAllProductsDTO getAllProductsByName(String productName, int page, int size);

    PaginatedResponseGetAllProductsDTO getAllProductsByStatus(boolean status, int page, int size);

    PaginatedResponseGetAllProductsDTO getAllProductsByCategory(CatagoryType category, int page, int size);;

    List<ResponseGetAllProductsDTO> getAllProductsNormal();

    Resource getImageByUrl(String url);
}
