package com.LittleLanka.product_service.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StandardResponse<T> { // No default type syntax here
    private int code;
    private String message;
    private T data;

    // Constructor for raw type usage (defaults to Object)
    public StandardResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = (T) data; // Cast to T (unsafe, but works for raw type usage)
    }
}