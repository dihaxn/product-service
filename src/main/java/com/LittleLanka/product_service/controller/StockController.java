package com.LittleLanka.product_service.controller;

import com.LittleLanka.product_service.dto.StockDTO;
import com.LittleLanka.product_service.dto.request.RequestStockUpdateDto;
import com.LittleLanka.product_service.dto.request.RequestUpdateStockDTO;
import com.LittleLanka.product_service.dto.response.ResponseInventryDto;
import com.LittleLanka.product_service.dto.response.ResponseStockDto;
import com.LittleLanka.product_service.dto.response.ResponseUpdateStockDTO;
import com.LittleLanka.product_service.service.StockService;
import com.LittleLanka.product_service.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/stock")
@AllArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/stock-initialize")
    public ResponseEntity<StandardResponse> initializeStock(@RequestBody StockDTO stockDTO) {
        StockDTO stockDTO1=stockService.initializeStock(stockDTO);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(),
                "Successfully initialized the stock",stockDTO1),
                HttpStatus.CREATED);
    }

    @PutMapping("stock-update-by-id-Qty")
    public ResponseEntity<StandardResponse> updateStockById(@RequestBody RequestStockUpdateDto requestStockUpdate) {
        StockDTO stockDTO=stockService.updateStockByIdQty(requestStockUpdate);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(),"Successfully updated stock",stockDTO),
                HttpStatus.CREATED);
    }

    @GetMapping ("all-stocks/{outletId}")
    public ResponseEntity<StandardResponse> getAllStocksOutlet(@PathVariable(value = "outletId")Long outletId) {
        List<ResponseStockDto> stockDtoList=stockService.getAllStocksOutlet(outletId);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(),"Successfully updated stock",stockDtoList),
                HttpStatus.CREATED);
    }

    @GetMapping ("all-info/{outletId}")
    public ResponseEntity<StandardResponse> getAllStockFullInfoOutlet(@PathVariable(value = "outletId")Long outletId) {
        List<ResponseInventryDto> stockInfoDtoList=stockService.getAllStockFullInfoOutlet(outletId);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(),"Successfully get  stock details",stockInfoDtoList),
                HttpStatus.CREATED);
    }

    @PutMapping("/update-stock-by-outletId-productList")
    public ResponseEntity<StandardResponse> updateStockByOutletIdAndProductList(@RequestBody RequestUpdateStockDTO requestUpdateStockDTO) {
        ResponseUpdateStockDTO responseUpdateStockDTO= stockService.updateStockByOutletIdAndProductList(requestUpdateStockDTO);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(),"Successfully updated stock",responseUpdateStockDTO),
                HttpStatus.CREATED);
    }

}
