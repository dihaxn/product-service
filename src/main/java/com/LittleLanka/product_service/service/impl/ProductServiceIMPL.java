package com.LittleLanka.product_service.service.impl;

import com.LittleLanka.product_service.dto.ProductDTO;
import com.LittleLanka.product_service.dto.paginated.PaginatedResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.request.*;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsWithStock;
import com.LittleLanka.product_service.dto.response.ResponseGetProductDTO;
import com.LittleLanka.product_service.entity.PriceUpdate;
import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.entity.Stock;
import com.LittleLanka.product_service.entity.enums.CatagoryType;
import com.LittleLanka.product_service.repository.PriceUpdateRepository;
import com.LittleLanka.product_service.repository.ProductRepository;
import com.LittleLanka.product_service.repository.StockRepository;
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
    private StockRepository stockRepository;
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

    @Override
    public ProductDTO updateProduct(RequestUpdateProductDTO requestUpdateProductDTO, int productId) {
        Optional<Product> existingProductOpt = productRepository.findById((long) productId);
        if (existingProductOpt.isEmpty()) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }

        Product existingProduct = existingProductOpt.get();
        MultipartFile image = requestUpdateProductDTO.getImageFile();
        String imageUrl = existingProduct.getImageUrl(); // Keep existing image if none is provided

        // Handle image update (delete old image if a new one is provided)
        if (image != null && !image.isEmpty()) {
            try {
                // Delete the old image file if it exists
                if (imageUrl != null) {
                    Path oldImagePath = Paths.get(imageUrl);
                    Files.deleteIfExists(oldImagePath);
                }

                // Save the new image
                String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path imagePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);
                Files.createDirectories(imagePath.getParent());
                Files.write(imagePath, image.getBytes());
                imageUrl = imagePath.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Update product details only if new values are provided
        existingProduct.setProductName(
                requestUpdateProductDTO.getProductName() != null ? requestUpdateProductDTO.getProductName() : existingProduct.getProductName()
        );
        existingProduct.setProductCatagory(
                requestUpdateProductDTO.getProductCatagory() != null ? requestUpdateProductDTO.getProductCatagory() : existingProduct.getProductCatagory()
        );
        existingProduct.setProductMeasuringUnitType(
                requestUpdateProductDTO.getProductMeasuringUnitType() != null ? requestUpdateProductDTO.getProductMeasuringUnitType() : existingProduct.getProductMeasuringUnitType()
        );
        existingProduct.setImageUrl(imageUrl);
        existingProduct.setProductStatus(requestUpdateProductDTO.isProductStatus());

        // Save updated product details
        Product updatedProduct = productRepository.save(existingProduct);

        // Convert string date to Date object if provided, else use the current date
        Date newDate = requestUpdateProductDTO.getDate() != null
                ? serviceFuntions.makeDate(requestUpdateProductDTO.getDate())
                : new Date();

        // Check if a new price is provided
        if (requestUpdateProductDTO.getPrice() != null) {
            Double newPrice = requestUpdateProductDTO.getPrice();

            // Check if a record with the same price and date already exists
            boolean exists = priceUpdateRepository.existsByProductAndPriceAndPriceUpdateDate(updatedProduct, newPrice, newDate);

            if (!exists) {
                // If not exists, add a new price update entry
                PriceUpdate priceUpdate = new PriceUpdate();
                priceUpdate.setProduct(updatedProduct);
                priceUpdate.setPrice(newPrice);
                priceUpdate.setPriceUpdateDate(newDate);
                priceUpdateRepository.save(priceUpdate);
            }
        }

        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public List<ResponseGetAllProductsWithStock> getAllProductByOutlet(int outletId) {
        List<Stock> stocks = stockRepository.findAllByOutletId((long) outletId);

        return stocks.stream().map(stock -> {
            Product product = stock.getProduct();
            ResponseGetAllProductsWithStock dto = modelMapper.map(product, ResponseGetAllProductsWithStock.class);
            dto.setStockQuantity(stock.getStockQuantity());

            // Fetch price using the specified function
            Double price = priceUpdateRepository.findPriceUpdateByPriceUpdateDateAndProductId(new Date(), product.getProductId());
            dto.setPrice(price != null ? price : 0.0);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ResponseGetProductDTO getProductById(int productId) {
        Product product = productRepository.findById((long) productId).get();
        Double todayPrice = priceUpdateRepository.findPriceUpdateByPriceUpdateDateAndProductId(new Date(), product.getProductId());
        // Get the latest price update
        PriceUpdate latestPriceUpdate = priceUpdateRepository.findTopByProductOrderByPriceUpdateDateDesc(product);
        Double lastUpdatedPrice = latestPriceUpdate != null ? latestPriceUpdate.getPrice() : null;
        String lastUpdatedDate = latestPriceUpdate != null ? latestPriceUpdate.getPriceUpdateDate().toString() : null;

        ResponseGetProductDTO responseDTO = modelMapper.map(product, ResponseGetProductDTO.class);
        responseDTO.setTodayPrice(todayPrice);
        responseDTO.setLastUpdatedPrice(lastUpdatedPrice);
        responseDTO.setLastUpdatedDate(lastUpdatedDate);

        return responseDTO;

    }

}
