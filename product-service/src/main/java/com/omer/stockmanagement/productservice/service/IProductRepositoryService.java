package com.omer.stockmanagement.productservice.service;

import com.omer.stockmanagement.productservice.enums.Language;
import com.omer.stockmanagement.productservice.repository.entity.Product;
import com.omer.stockmanagement.productservice.request.ProductCreateRequest;
import com.omer.stockmanagement.productservice.request.ProductUpdateRequest;

import java.util.List;

public interface IProductRepositoryService {

    Product createProduct(Language language, ProductCreateRequest productCreateRequest);

    Product getProduct(Language language, Long productId);

    List<Product> getAllProducts(Language language);

    Product updateProduct(Language language, Long productId, ProductUpdateRequest productUpdateRequest);

    Product deleteProduct(Language language, Long productId);

}
