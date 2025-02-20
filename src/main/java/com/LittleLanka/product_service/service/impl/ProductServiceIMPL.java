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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceIMPL implements ProductService {
    private ProductRepository productRepository;
    private PriceUpdateRepository priceUpdateRepository;
    private ModelMapper modelMapper;
    private ServiceFuntions serviceFuntions;
    private final String IMAGE_UPLOAD_DIR = "src/main/java/com/LittleLanka/product_service/assets/";


    @Override
    public ProductDTO saveProduct(RequestSaveProductDto requestSaveProductDTO) {
        MultipartFile image = requestSaveProductDTO.getImageFile();
        String imageUrl = null;
        if(image != null && !image.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
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

    @Override
    public List<ResponseGetAllProductsDTO> getAllProductsNormal() {
        List<Product> products = productRepository.findAll(); // Fetch all products

        // Map all products to ResponseGetAllProductsDTO
        return products.stream()
                .map(product -> {
                    ResponseGetAllProductsDTO productDTO = modelMapper.map(product, ResponseGetAllProductsDTO.class);

                    // Fetch and set the latest price
                    Double price = priceUpdateRepository.findPriceUpdateByPriceUpdateDateAndProductId(new Date(), product.getProductId());
                    productDTO.setPrice(price);

                    return productDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Resource getImageByUrl(String url) {
        String imagePath = IMAGE_UPLOAD_DIR + url;
        File file = new File(imagePath);

        // Ensure the file exists
        if (!file.exists()) {
            throw new RuntimeException("Image not found: " + url);
        }

        // Create a resource from the image file
        return new FileSystemResource(file);
    }


}
