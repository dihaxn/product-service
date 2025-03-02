package com.LittleLanka.product_service.controller;

import com.LittleLanka.product_service.dto.ProductDTO;
import com.LittleLanka.product_service.dto.paginated.PaginatedResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.request.RequestSaveProductDto;
import com.LittleLanka.product_service.dto.request.RequestUpdateProductDTO;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsWithStock;
import com.LittleLanka.product_service.entity.enums.CatagoryType;
import com.LittleLanka.product_service.service.ProductService;
import com.LittleLanka.product_service.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    // update a product
    @PutMapping(consumes = {"multipart/form-data"}, params = "id")
    public ResponseEntity<StandardResponse> updateProduct(
            @ModelAttribute RequestUpdateProductDTO requestUpdateProductDTO,
            @RequestParam(value = "id") int productId
    ) {
        ProductDTO updatedProduct = productService.updateProduct(requestUpdateProductDTO, productId);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(), "Successfully saved the product",updatedProduct),
                HttpStatus.CREATED);
    }

    @GetMapping(path = "get-by-outlet", params = "id")
    public ResponseEntity<StandardResponse> getAllProductByOutlet(@RequestParam(value = "id") int outletId) {
        List<ResponseGetAllProductsWithStock> products = productService.getAllProductByOutlet(outletId);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully fetch all products", products),
                HttpStatus.OK);
    }

    // get all products
    @GetMapping("/all")
    public ResponseEntity<StandardResponse> getAllProductsNormal() {
        List<ResponseGetAllProductsDTO> allProducts = productService.getAllProductsNormal();
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully fetch all products", allProducts),
                HttpStatus.OK);
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

    // get image by url
    @GetMapping("/url/{url}")
    public ResponseEntity<Resource> getImageByUrl(@PathVariable(value = "url") String url) {
        Resource imageResource = productService.getImageByUrl(url);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageResource);
    }

}
