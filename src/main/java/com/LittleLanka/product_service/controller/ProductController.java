package com.LittleLanka.product_service.controller;

import com.LittleLanka.product_service.dto.ProductDTO;
import com.LittleLanka.product_service.dto.request.RequestSaveProductDto;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.service.ProductService;
import com.LittleLanka.product_service.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/save-product", consumes = { "multipart/form-data" })
    public ResponseEntity<StandardResponse> saveProduct(@ModelAttribute RequestSaveProductDto requestSaveProductDTO) {
        ProductDTO productDTO = productService.saveProduct(requestSaveProductDTO);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(), "Successfully saved the product",productDTO),
                HttpStatus.CREATED);
    }

    @GetMapping(value = "/get-all-products")
    public ResponseEntity<StandardResponse> getAllProducts() {
        List<ResponseGetAllProductsDTO> allProductsDTOS = productService.getAllProducts();
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully fetch all products", allProductsDTOS),
                HttpStatus.OK);
    }

    @GetMapping(value = "/get-all-products-by-name/{name}")
    public ResponseEntity<StandardResponse> getAllProductsByName(@PathVariable(value = "name") String productName) {
        List<ResponseGetAllProductsDTO> allProductsDTOS = productService.getAllProductsByName(productName);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully fetch all products", allProductsDTOS),
                HttpStatus.OK);
    }

}
