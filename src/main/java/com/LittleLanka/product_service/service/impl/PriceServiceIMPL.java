package com.LittleLanka.product_service.service.impl;

import com.LittleLanka.product_service.dto.PriceUpdateDTO;
import com.LittleLanka.product_service.dto.queryInterfaces.PriceListInterface;
import com.LittleLanka.product_service.dto.request.RequestDateAndPriceListDTO;
import com.LittleLanka.product_service.dto.request.RequestPriceUpdateDto;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.response.ResponsePriceListDTO;
import com.LittleLanka.product_service.entity.PriceUpdate;
import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.repository.PriceUpdateRepository;
import com.LittleLanka.product_service.repository.ProductRepository;
import com.LittleLanka.product_service.service.PriceService;
import com.LittleLanka.product_service.util.functions.ServiceFuntions;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PriceServiceIMPL implements PriceService {
    private PriceUpdateRepository priceUpdateRepository;
    private ProductRepository productRepository;
    private ModelMapper modelMapper;
    private ServiceFuntions serviceFuntions;


    @Override
    public Double getPriceByDateAndProductId(String date, Long id) {
        Date dateObj = serviceFuntions.makeDate(date);
        return priceUpdateRepository.findPriceUpdateByPriceUpdateDateAndProductId(dateObj, id);
    }

    @Override
    public List<ResponsePriceListDTO> getPriceListByDate(String date) {
        Date dateObj = serviceFuntions.makeDate(date);
        List<PriceListInterface> priceListDTOS = priceUpdateRepository.findProductIdAndPriceByDateEquals(dateObj);
        if (priceListDTOS.isEmpty()) {
            throw new RuntimeException("Not found price list");
        }
        List<ResponsePriceListDTO> responsePriceListDTOS = new ArrayList<>();
        for (PriceListInterface priceListDTO : priceListDTOS) {
            ResponsePriceListDTO responsePriceListDTO = new ResponsePriceListDTO(
                    priceListDTO.getProductId(),
                    priceListDTO.getPrice()
            );
            responsePriceListDTOS.add(responsePriceListDTO);
        }
        return responsePriceListDTOS;
    }

    @Override
    public PriceUpdateDTO updatePrice(RequestPriceUpdateDto requestPriceUpdateDto) {
        PriceUpdate priceUpdate=new PriceUpdate();
        priceUpdate.setPrice(requestPriceUpdateDto.getPrice());
        priceUpdate.setPriceUpdateDate(requestPriceUpdateDto.getPriceUpdateDate());
        Product product=productRepository.findById(requestPriceUpdateDto.getProductId()).get();
        priceUpdate.setProduct(product);
        PriceUpdate priceUpdateOut=priceUpdateRepository.save(priceUpdate);
        return modelMapper.map(priceUpdateOut, PriceUpdateDTO.class);
    }

    @Override
    public List<ResponseGetAllProductsDTO> getPriceListByDateAndProductIdList(RequestDateAndPriceListDTO requestDateAndPriceListDTO) {
        String date = requestDateAndPriceListDTO.getDate();
        Date dateObj = serviceFuntions.makeDate(date);  // Convert string date to Date object

        List<Long> productIds = requestDateAndPriceListDTO.getProductIds();
        List<Product> products = productRepository.findAllById(productIds); // Fetch products from Product table

        List<ResponseGetAllProductsDTO> responseList = new ArrayList<>();

        for (Product product : products) {
            Double price = priceUpdateRepository.findPriceUpdateByPriceUpdateDateAndProductId(dateObj, product.getProductId());

            // Map Product to ResponseGetAllProductsDTO
            ResponseGetAllProductsDTO responseDTO = modelMapper.map(product, ResponseGetAllProductsDTO.class);
            responseDTO.setPrice(price); // Set price from price update table

            responseList.add(responseDTO);
        }

        return responseList;
    }

    @Override
    public PriceUpdateDTO updatePriceUpdateStatus(Long id, double price, String date, boolean status) {
        Date dateObj = serviceFuntions.makeDate(date);
        PriceUpdate priceUpdate = priceUpdateRepository.findPriceUpdateByProduct_ProductIdAndPriceAndPriceUpdateDateEquals(id, price, dateObj);
        priceUpdate.setPriceUpdateStatus(status);
        PriceUpdate priceUpdateOut=priceUpdateRepository.save(priceUpdate);
        return modelMapper.map(priceUpdateOut, PriceUpdateDTO.class);
    }
}
