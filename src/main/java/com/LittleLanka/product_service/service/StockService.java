package com.LittleLanka.product_service.service;

import com.LittleLanka.product_service.dto.StockDTO;
import com.LittleLanka.product_service.dto.request.RequestInitializeStockDto;
import com.LittleLanka.product_service.dto.request.RequestStockUpdateDto;
import com.LittleLanka.product_service.dto.request.RequestUpdateStockDTO;
import com.LittleLanka.product_service.dto.response.ResponseInventryDto;
import com.LittleLanka.product_service.dto.response.ResponseStockDto;
import com.LittleLanka.product_service.dto.response.ResponseUpdateStockDTO;

import java.util.List;

public interface StockService {
    StockDTO initializeStock(RequestInitializeStockDto requestInitializeStockDto);

    StockDTO updateStockByIdQty(RequestStockUpdateDto requestStockUpdate);

    List<ResponseStockDto> getAllStocksOutlet(Long outletId);

    ResponseUpdateStockDTO updateStockByOutletIdAndProductList(RequestUpdateStockDTO requestUpdateStockDTO);

    List<ResponseInventryDto> getAllStockFullInfoOutlet(Long outletId);
}
