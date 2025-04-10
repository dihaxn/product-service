package com.LittleLanka.product_service.service.impl;

import com.LittleLanka.product_service.dto.StockDTO;
import com.LittleLanka.product_service.dto.request.RequestInitializeStockDto;
import com.LittleLanka.product_service.dto.request.RequestProductListDTO;
import com.LittleLanka.product_service.dto.request.RequestStockUpdateDto;
import com.LittleLanka.product_service.dto.request.RequestUpdateStockDTO;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.response.ResponseInventryDto;
import com.LittleLanka.product_service.dto.response.ResponseStockDto;
import com.LittleLanka.product_service.dto.response.ResponseUpdateStockDTO;
import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.entity.Stock;
import com.LittleLanka.product_service.exception.InvalidStockException;
import com.LittleLanka.product_service.exception.ProductNotFoundException;
import com.LittleLanka.product_service.repository.PriceUpdateRepository;
import com.LittleLanka.product_service.repository.ProductRepository;
import com.LittleLanka.product_service.repository.StockRepository;
import com.LittleLanka.product_service.service.StockService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StockServiceIMPL implements StockService {
    private ModelMapper modelMapper;
    private StockRepository stockRepository;
    private ProductRepository productRepository;
    private PriceUpdateRepository priceUpdateRepository;

    @Override
    public StockDTO initializeStock(RequestInitializeStockDto requestInitializeStockDto) {
        // Validate if the product exists
        Product product = productRepository.findById(requestInitializeStockDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + requestInitializeStockDto.getProductId()));

        // Check if a stock record exists for the given outletId and productId
        if (stockRepository.findByOutletIdAndProduct(requestInitializeStockDto.getOutletId(), product).isPresent()) {
            throw new InvalidStockException("Stock already exists for the product at the given outlet");
        }

        // Create a new stock record
        Stock newStock = new Stock();
        newStock.setOutletId(requestInitializeStockDto.getOutletId());
        newStock.setStockQuantity(requestInitializeStockDto.getStockQuantity());
        newStock.setProduct(product);

        Stock savedStock = stockRepository.save(newStock);
        return modelMapper.map(savedStock, StockDTO.class);
    }

    @Override
    public StockDTO updateStockByIdQty(RequestStockUpdateDto requestStockUpdate) {
        Stock stock=stockRepository.getReferenceById(requestStockUpdate.getStockId());
        double updatedQty=stock.getStockQuantity()-requestStockUpdate.getStockQuantity();
        stock.setStockQuantity(updatedQty);
        Stock stock1=stockRepository.save(stock);
        return modelMapper.map(stock1, StockDTO.class);
    }

    @Override
    public List<ResponseStockDto> getAllStocksOutlet(Long outletId) {
        List<Stock> stockList=stockRepository.getAllByOutletId(outletId);
        List<ResponseStockDto> responseStockDtoList=new ArrayList<>();

        for(Stock stock:stockList){
            responseStockDtoList.add(new ResponseStockDto().builder()
                    .productId(stock.getProduct().getProductId())
                    .stockQuantity(stock.getStockQuantity())
                    .productName(stock.getProduct().getProductName())
                    .build());
        }
        return responseStockDtoList;
    }

    @Override
    public ResponseUpdateStockDTO updateStockByOutletIdAndProductList(RequestUpdateStockDTO requestUpdateStockDTO) {
        Long outletId = requestUpdateStockDTO.getOutletId();
        List<RequestProductListDTO> productList = requestUpdateStockDTO.getProductList();
        boolean isIncrease = requestUpdateStockDTO.isIncrease();
        List<RequestProductListDTO> updatedProductList = new ArrayList<>();

        for (RequestProductListDTO productDTO : productList) {
            Product product = productRepository.getReferenceById(productDTO.getProductId());
            Optional<Stock> stockOpt = stockRepository.findByOutletIdAndProduct(outletId, product);

            Stock stock;
            double newStockQuantity;

            if (stockOpt.isPresent()) {
                stock = stockOpt.get();
                if (isIncrease) {
                    newStockQuantity = stock.getStockQuantity() + productDTO.getStockQuantity();
                } else {
                    if (stock.getStockQuantity() < productDTO.getStockQuantity()) {
                        throw new RuntimeException("Insufficient stock for productId: " + productDTO.getProductId());
                    }
                    newStockQuantity = stock.getStockQuantity() - productDTO.getStockQuantity();
                }
                stock.setStockQuantity(newStockQuantity);
            } else {
                // Create new stock if not found
                if (!isIncrease) {
                    throw new RuntimeException("Cannot decrease stock for non-existing stock entry for productId: " + productDTO.getProductId());
                }
                stock = new Stock();
                stock.setOutletId(outletId);
                stock.setProduct(product);
                newStockQuantity = productDTO.getStockQuantity();
                stock.setStockQuantity(newStockQuantity);
            }

            stockRepository.save(stock);
            updatedProductList.add(new RequestProductListDTO(productDTO.getProductId(), newStockQuantity));
        }

        return new ResponseUpdateStockDTO(outletId, updatedProductList);
    }



    @Override
    public List<ResponseInventryDto> getAllStockFullInfoOutlet(Long outletId) {
        List<Stock> stockList=stockRepository.getAllByOutletId(outletId);
        Product product;
        List<ResponseInventryDto> responseInventryDtoList=new ArrayList<>();


        for(Stock stock:stockList){
            product=productRepository.getReferenceById(stock.getProduct().getProductId());
            Double price = priceUpdateRepository.findPriceUpdateByPriceUpdateDateAndProductId(new Date(), product.getProductId());

            responseInventryDtoList.add(new ResponseInventryDto().builder()
                    .productId(stock.getProduct().getProductId())
                    .stockQuantity(stock.getStockQuantity())
                    .productName(stock.getProduct().getProductName())
                    .stockQuantity(stock.getStockQuantity())
                    .imageUrl(product.getImageUrl())
                    .price(price)
                    .build());
        }
        return List.of();
    }
}
