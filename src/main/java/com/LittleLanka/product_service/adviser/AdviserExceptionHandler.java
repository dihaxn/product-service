package com.LittleLanka.product_service.adviser;

import com.LittleLanka.product_service.exception.*;
import com.LittleLanka.product_service.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviserExceptionHandler {
    @ExceptionHandler(DuplicateProductException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(DuplicateProductException e) {
        return new ResponseEntity<>(new StandardResponse(400, "error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(ProductNotFoundException e) {
        return new ResponseEntity<>(new StandardResponse(404, "error", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidStockException.class)
    public ResponseEntity<StandardResponse> handleDuplicateEntityException(InvalidStockException e) {
        return new ResponseEntity<>(new StandardResponse(400, "error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<StandardResponse> handleInvalidDataException(InvalidDateException e) {
        return new ResponseEntity<>(new StandardResponse(400, "error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<StandardResponse> handleInsufficientStockException(InsufficientStockException e) {
        return new ResponseEntity<>(new StandardResponse(400, "error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<StandardResponse> handleImageProcessingException(ImageUploadException e) {
        return new ResponseEntity<>(new StandardResponse(500, "error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<StandardResponse> handleStockNotFoundException(StockNotFoundException e) {
        return new ResponseEntity<>(new StandardResponse(404, "error", e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
