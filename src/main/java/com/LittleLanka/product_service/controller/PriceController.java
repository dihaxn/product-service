package com.LittleLanka.product_service.controller;

import com.LittleLanka.product_service.dto.PriceUpdateDTO;
import com.LittleLanka.product_service.dto.request.RequestDateAndPriceListDTO;
import com.LittleLanka.product_service.dto.request.RequestPriceUpdateDto;
import com.LittleLanka.product_service.dto.response.ResponseGetAllProductsDTO;
import com.LittleLanka.product_service.dto.response.ResponsePriceListDTO;
import com.LittleLanka.product_service.dto.response.ResponsePriceUpdateDTO;
import com.LittleLanka.product_service.service.PriceService;
import com.LittleLanka.product_service.util.StandardResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/price")
@AllArgsConstructor
public class PriceController {
    private PriceService priceService;

    // Update a product's price
    @PutMapping
    public ResponseEntity<StandardResponse> updatePrice(@RequestBody RequestPriceUpdateDto requestPriceUpdateDto) {
        PriceUpdateDTO priceUpdateDTO= priceService.updatePrice(requestPriceUpdateDto);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.CREATED.value(), "Successfully updated price",priceUpdateDTO),
                HttpStatus.CREATED);
    }

    // Get price by date and product ID
    @GetMapping("/{date}/{id}")
    public ResponseEntity<StandardResponse> getPriceByDateAndProductId(
            @PathVariable("date") String date,
            @PathVariable("id") Long id)
    {
        Double price = priceService.getPriceByDateAndProductId(date, id);
        return new ResponseEntity<>(
                new StandardResponse(HttpStatus.OK.value(), "Successfully retrieved price", price),
                HttpStatus.OK);
    }

    // Get price list by date
    @GetMapping("/list/{date}")
    public ResponseEntity<StandardResponse> getPriceListByDate(@PathVariable(value = "date") String date) {
        List<ResponsePriceListDTO> priceListDTOS = priceService.getPriceListByDate(date);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(),"Successfully loaded the price list",
                priceListDTOS), HttpStatus.OK);
    }

    // Get price list by date and product ID list
    @PostMapping("/list-by-product-ids")
    public ResponseEntity<StandardResponse<List<ResponseGetAllProductsDTO>>> getPriceListByDateAndProductIdList(
            @RequestBody RequestDateAndPriceListDTO requestDateAndPriceListDTO) {
        List<ResponseGetAllProductsDTO> priceListDTOS = priceService.getPriceListByDateAndProductIdList(requestDateAndPriceListDTO);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully got the price list",
                priceListDTOS), HttpStatus.OK);
    }

    @PutMapping("status/{productId}/{price}/{date}/{status}")
    public ResponseEntity<StandardResponse> updatePriceUpdateStatus(
            @PathVariable(value = "productId") Long id,
            @PathVariable(value = "price") double price,
            @PathVariable(value = "date") String date,
            @PathVariable(value = "status") boolean status
    ){
        PriceUpdateDTO priceUpdateDTO = priceService.updatePriceUpdateStatus(id, price, date, status);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully updated stauts", priceUpdateDTO),
                HttpStatus.CREATED);
    }

    @GetMapping("status")
    public ResponseEntity<StandardResponse> getAllPriceByStatus(){
        List<ResponsePriceUpdateDTO> priceUpdateDTO = priceService.getAllPriceByStatus(false);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully fetched priceUpdates", priceUpdateDTO),
                HttpStatus.CREATED);
    }

    @DeleteMapping("status/{productId}/{price}/{date}")
    public ResponseEntity<StandardResponse> deletePriceUpdateStatus(
            @PathVariable(value = "productId") Long id,
            @PathVariable(value = "price") double price,
            @PathVariable(value = "date") String date
    ){
        Long priceUpdateStatus = priceService.deletePriceUpdateStatus(id, price, date);
        return new ResponseEntity<>(new StandardResponse(HttpStatus.OK.value(), "Successfully deleted stauts", priceUpdateStatus),
                HttpStatus.CREATED);
    }
}
