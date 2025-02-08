package com.LittleLanka.product_service.controller;

import com.LittleLanka.product_service.dto.ProductDTO;
import com.LittleLanka.product_service.dto.paginated.PaginatedResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.request.RequestSaveProductDto;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.entity.enums.CatagoryType;
import com.LittleLanka.product_service.service.ProductService;
import com.LittleLanka.product_service.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("api/v1/product")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    // save a product
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<StandardResponse> saveProduct(@ModelAttribute RequestSaveProductDto requestSaveProductDTO) {
        ProductDTO productDTO = productService.saveProduct(requestSaveProductDTO);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(), "Successfully saved the product",productDTO),
                HttpStatus.CREATED);
    }

    // get all products
    @GetMapping
    public ResponseEntity<StandardResponse> getAllProducts(
            @RequestParam int page,
            @RequestParam int size
    ) {
        PaginatedResponseGetAllProductsDTO paginatedAllProducts = productService.getAllProducts(page, size);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully fetch all products", paginatedAllProducts),
                HttpStatus.OK);
    }

    // get all products by name
    @GetMapping("/name/{name}")
    public ResponseEntity<StandardResponse> getAllProductsByName(
            @PathVariable(value = "name") String productName,
            @RequestParam int page,
            @RequestParam int size
    ) {
        PaginatedResponseGetAllProductsDTO paginatedAllProducts = productService.getAllProductsByName(productName, page, size);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully fetch all products", paginatedAllProducts),
                HttpStatus.OK);
    }

    // get all products by status
    @GetMapping(value = "/status/{status}")
    public ResponseEntity<StandardResponse> getAllProductsByStatus(
            @PathVariable(value = "status") boolean status,
            @RequestParam int page,
            @RequestParam int size
    ) {
        PaginatedResponseGetAllProductsDTO paginatedAllProducts = productService.getAllProductsByStatus(status, page, size);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully fetch all products", paginatedAllProducts),
                HttpStatus.OK);
    }

    // get all products by category
    @GetMapping("category/{category}")
    public ResponseEntity<StandardResponse> getAllProductsByCategory(
            @PathVariable(value = "category") CatagoryType category,
            @RequestParam int page,
            @RequestParam int size
    ) {
        PaginatedResponseGetAllProductsDTO paginatedAllProducts = productService.getAllProductsByCategory(category, page, size);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully fetch all products", paginatedAllProducts),
                HttpStatus.OK);
    }

}
