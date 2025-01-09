package com.LittleLanka.product_service.util.functions;

import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.repository.PriceUpdateRepository;
import com.LittleLanka.product_service.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ServiceFuntions {
    @Autowired
    private PriceUpdateRepository priceUpdateRepository;
    @Autowired
    private ModelMapper modelMapper;

    public Date makeDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = null;
        try {
            dateObj = formatter.parse(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dateObj;
    }

    public List<ResponseGetAllProductsDTO> getResponseGetAllProductsDTOS(List<Product> products) {
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
