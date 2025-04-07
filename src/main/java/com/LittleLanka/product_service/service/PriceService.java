package com.LittleLanka.product_service.service;

import com.LittleLanka.product_service.dto.PriceUpdateDTO;
import com.LittleLanka.product_service.dto.request.RequestDateAndPriceListDTO;
import com.LittleLanka.product_service.dto.request.RequestPriceUpdateDto;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.response.ResponsePriceListDTO;
import com.LittleLanka.product_service.dto.response.ResponsePriceUpdateDTO;

import java.util.List;

public interface PriceService {
    PriceUpdateDTO updatePrice(RequestPriceUpdateDto requestPriceUpdateDto);

    Double getPriceByDateAndProductId(String date, Long id);

    List<ResponsePriceListDTO> getPriceListByDate(String date);

    List<ResponseGetAllProductsDTO> getPriceListByDateAndProductIdList(RequestDateAndPriceListDTO requestDateAndPriceListDTO);

    List<ResponsePriceUpdateDTO> getAllPriceByStatus(boolean b);

    Long deletePriceUpdateStatus(Long id, double price, String date);

    PriceUpdateDTO updatePriceUpdateStatus(Long id, double price, String date, boolean status);
}
