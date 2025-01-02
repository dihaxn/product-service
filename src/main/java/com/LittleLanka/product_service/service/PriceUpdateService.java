package com.LittleLanka.product_service.service;

import com.LittleLanka.product_service.dto.PriceUpdateDTO;
import com.LittleLanka.product_service.dto.request.RequestDateAndPriceListDTO;
import com.LittleLanka.product_service.dto.request.RequestPriceUpdateDto;
import com.LittleLanka.product_service.dto.response.ResponsePriceListDTO;

import java.util.List;

public interface PriceUpdateService {
    PriceUpdateDTO updatePrice(RequestPriceUpdateDto requestPriceUpdateDto);

    Double getPriceByDateAndProductId(String date, Long id);

    List<ResponsePriceListDTO> getPriceListByDate(String date);

    List<ResponsePriceListDTO> getPriceListByDateAndProductIdList(RequestDateAndPriceListDTO requestDateAndPriceListDTO);
}
