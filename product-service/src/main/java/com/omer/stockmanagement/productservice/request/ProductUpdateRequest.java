package com.omer.stockmanagement.productservice.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private long id;
    private String productName;
    private Integer quantity;
    private Double price;
}
