package com.omer.stockmanagement.productservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {
    private long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Date productCreatedDate;
    private Date productUpdatedDate;
}
