package com.LittleLanka.product_service.service.impl;

import com.LittleLanka.product_service.dto.PriceUpdateDTO;
import com.LittleLanka.product_service.dto.queryInterfaces.PriceListInterface;
import com.LittleLanka.product_service.dto.request.RequestDateAndPriceListDTO;
import com.LittleLanka.product_service.dto.request.RequestPriceUpdateDto;
import com.LittleLanka.product_service.dto.response.ResponsePriceListDTO;
import com.LittleLanka.product_service.entity.PriceUpdate;
import com.LittleLanka.product_service.entity.Product;
import com.LittleLanka.product_service.repository.PriceUpdateRepository;
import com.LittleLanka.product_service.repository.ProductRepository;
import com.LittleLanka.product_service.service.PriceService;
import com.LittleLanka.product_service.util.functions.ServiceFuntions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PriceServiceIMPL implements PriceService {
    @Autowired
    private PriceUpdateRepository priceUpdateRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
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
    public List<ResponsePriceListDTO> getPriceListByDateAndProductIdList(RequestDateAndPriceListDTO requestDateAndPriceListDTO) {
        String date = requestDateAndPriceListDTO.getDate();
        List<ResponsePriceListDTO> priceListByDate = getPriceListByDate(date); // Fetch all prices for the date
        // Filter the prices based on the product IDs provided in the request
        List<Long> productIds = requestDateAndPriceListDTO.getProductIds();
        List<ResponsePriceListDTO> filteredPriceList = new ArrayList<>();
        for (ResponsePriceListDTO priceListDTO : priceListByDate) {
            if (productIds.contains(priceListDTO.getProductId())) {
                filteredPriceList.add(priceListDTO);
            }
        }
        return filteredPriceList;
    }
}
