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



    // initialize a stock
    @PostMapping
    public ResponseEntity<StandardResponse> initializeStock(@RequestBody StockDTO stockDTO) {
        StockDTO stockDTO1=stockService.initializeStock(stockDTO);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(),
                "Successfully initialized the stock",stockDTO1),
                HttpStatus.CREATED);
    }

    // update stock by id
    @PutMapping
    public ResponseEntity<StandardResponse> updateStockById(@RequestBody RequestStockUpdateDto requestStockUpdate) {
        StockDTO stockDTO=stockService.updateStockByIdQty(requestStockUpdate);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(),"Successfully updated stock",stockDTO),
                HttpStatus.CREATED);
    }


    // get stock by outlet id
    @GetMapping("/{outletId}")
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


    // get stock by outlet id and product list
    @PutMapping("/by-outletId-productList")
    public ResponseEntity<StandardResponse> updateStockByOutletIdAndProductList(@RequestBody RequestUpdateStockDTO requestUpdateStockDTO) {
        ResponseUpdateStockDTO responseUpdateStockDTO= stockService.updateStockByOutletIdAndProductList(requestUpdateStockDTO);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(),"Successfully updated stock",responseUpdateStockDTO),
                HttpStatus.CREATED);
    }

}
