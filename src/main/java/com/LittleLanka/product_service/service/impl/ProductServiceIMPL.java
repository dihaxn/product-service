package com.LittleLanka.product_service.service.impl;

import com.LittleLanka.product_service.dto.ProductDTO;
import com.LittleLanka.product_service.dto.request.*;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.repository.PriceUpdateRepository;
import com.LittleLanka.product_service.repository.ProductRepository;
import com.LittleLanka.product_service.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceIMPL implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceUpdateRepository priceUpdateRepository;
    @Autowired
    private ModelMapper modelMapper;
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
    public List<ResponseGetAllProductsDTO> getAllProducts() {
        List<Product> products = productRepository.findAllByProductStatusEquals(true);
        List<ResponseGetAllProductsDTO> responseGetAllProductsDTOList = new ArrayList<>();
        for (Product product : products) {
            ResponseGetAllProductsDTO allProductsDTO = modelMapper.map(product, ResponseGetAllProductsDTO.class);
            Double price = priceUpdateRepository.findPriceUpdateByPriceUpdateDateAndProductId(new Date(), product.getProductId());
            allProductsDTO.setPrice(price);
            responseGetAllProductsDTOList.add(allProductsDTO);
        }
        return responseGetAllProductsDTOList;
    }

}
