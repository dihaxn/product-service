package com.LittleLanka.product_service.service;

import com.LittleLanka.product_service.dto.StockDTO;
import com.LittleLanka.product_service.dto.request.RequestStockUpdateDto;
import com.LittleLanka.product_service.dto.response.ResponseStockDto;

import java.util.List;

public interface StockService {
    StockDTO initializeStock(StockDTO stockDTO);
    StockDTO updateStockByIdQty(RequestStockUpdateDto requestStockUpdate);

    List<ResponseStockDto> getAllStocksOutlet(Long outletId);
}
