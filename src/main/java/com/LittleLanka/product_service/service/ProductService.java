package com.LittleLanka.product_service.service;

import com.LittleLanka.product_service.dto.ProductDTO;
import com.LittleLanka.product_service.dto.request.*;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;

import java.util.List;


public interface ProductService {
    ProductDTO saveProduct(RequestSaveProductDto requestSaveProductDTO);

    List<ResponseGetAllProductsDTO> getAllProducts();

    List<ResponseGetAllProductsDTO> getAllProductsByName(String productName);

}
