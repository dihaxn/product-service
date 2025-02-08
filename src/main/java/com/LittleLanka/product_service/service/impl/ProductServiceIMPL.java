package com.LittleLanka.product_service.service.impl;

import com.LittleLanka.product_service.dto.ProductDTO;
import com.LittleLanka.product_service.dto.paginated.PaginatedResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.request.*;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.entity.enums.CatagoryType;
import com.LittleLanka.product_service.repository.PriceUpdateRepository;
import com.LittleLanka.product_service.repository.ProductRepository;
import com.LittleLanka.product_service.service.ProductService;
import com.LittleLanka.product_service.util.functions.ServiceFuntions;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class ProductServiceIMPL implements ProductService {
    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private ServiceFuntions serviceFuntions;


    @Override
    public ProductDTO saveProduct(RequestSaveProductDto requestSaveProductDTO) {
        MultipartFile image = requestSaveProductDTO.getImageFile();
        String imageUrl = null;
        if(image != null && !image.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                String IMAGE_UPLOAD_DIR = "src/main/java/com/LittleLanka/product_service/assets/";
                Path imagePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, image.getBytes());
                imageUrl = imagePath.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Product product = modelMapper.map(requestSaveProductDTO, Product.class);
        product.setImageUrl(imageUrl);
        if(!productRepository.existsByProductCatagoryAndProductName(requestSaveProductDTO.getProductCatagory()
                , requestSaveProductDTO.getProductName()))
        {
            productRepository.save(product);
            return modelMapper.map(product, ProductDTO.class);
        }
        return null;

    }

    @Override
    public PaginatedResponseGetAllProductsDTO getAllProducts(int page, int size) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));
        return serviceFuntions.getResponseGetAllProductsDTOS(products);
    }

    @Override
    public PaginatedResponseGetAllProductsDTO getAllProductsByName(String productName, int page, int size) {
        Page<Product> products = productRepository.findByProductNameContainingIgnoreCaseAndProductStatusIsTrue(
                productName, PageRequest.of(page, size, Sort.by("productName"))
        );

        List<Product> sortedProducts = new ArrayList<>(products.getContent());
        sortedProducts.sort(Comparator.comparing(product -> !product.getProductName().toLowerCase().startsWith(productName.toLowerCase())));

        Page<Product> sortedPage = new PageImpl<>(sortedProducts, products.getPageable(), products.getTotalElements());
        return serviceFuntions.getResponseGetAllProductsDTOS(sortedPage);

    }

    @Override
    public PaginatedResponseGetAllProductsDTO getAllProductsByStatus(boolean status, int page, int size) {
        Page<Product> products = productRepository.findAllByProductStatusEquals(status, PageRequest.of(page, size));
        return serviceFuntions.getResponseGetAllProductsDTOS(products);
    }

    @Override
    public PaginatedResponseGetAllProductsDTO getAllProductsByCategory(CatagoryType category, int page, int size) {
        Page<Product> products = productRepository.findAllByProductCatagoryEquals(category, PageRequest.of(page, size));
        return serviceFuntions.getResponseGetAllProductsDTOS(products);
    }

}
