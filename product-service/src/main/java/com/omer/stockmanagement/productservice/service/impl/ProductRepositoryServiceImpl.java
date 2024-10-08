package com.omer.stockmanagement.productservice.service.impl;

import com.omer.stockmanagement.productservice.enums.Language;
import com.omer.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.omer.stockmanagement.productservice.exception.exceptions.ProductNotCreatedException;
import com.omer.stockmanagement.productservice.exception.exceptions.ProductNotFoundException;
import com.omer.stockmanagement.productservice.repository.ProductRepository;
import com.omer.stockmanagement.productservice.repository.entity.Product;
import com.omer.stockmanagement.productservice.request.ProductCreateRequest;
import com.omer.stockmanagement.productservice.request.ProductUpdateRequest;
import com.omer.stockmanagement.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryServiceImpl implements IProductRepositoryService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}][createProduct] -> request : {}", this.getClass().getSimpleName(), productCreateRequest);
        try {
            Product product = Product.builder()
                .productName(productCreateRequest.getProductName())
                .quantity(productCreateRequest.getQuantity())
                .price(productCreateRequest.getPrice())
                .deleted(false)
                .build();
            Product productResponse = productRepository.save(product);
            log.debug("[{}][createProduct] -> response : {}", this.getClass().getSimpleName(), productResponse);
            return productResponse;
        }catch (Exception e) {
            throw new ProductNotCreatedException(language, FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION, "product request: " + productCreateRequest.toString());
        }
    }

    @Override
    public Product getProduct(Language language, Long productId) {
        log.debug("[{}][getProduct] -> request productId : {}", this.getClass().getSimpleName(), productId);
        Product product = productRepository.getByProductIdAndDeletedFalse(productId);
        if (Objects.isNull(product)) {
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "product not found for product id: " + productId);
        }
        log.debug("[{}][getProduct] -> response : {}", this.getClass().getSimpleName(), product);
        return product;
    }

    @Override
    public List<Product> getAllProducts(Language language) {
        log.debug("[{}][getAllProducts]", this.getClass().getSimpleName());
        List<Product> products = productRepository.getAllByDeletedFalse();
        if (products.isEmpty()) {
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "no products found");
        }
        log.debug("[{}][getAllProducts] -> response : {}", this.getClass().getSimpleName(), products);
        return products;
    }

    @Override
    public Product updateProduct(Language language, Long productId, ProductUpdateRequest productUpdateRequest) {
        log.debug("[{}][updateProduct] -> request productId : {}", this.getClass().getSimpleName(), productId);
        Product product = getProduct(language, productId);
        product.setProductName(productUpdateRequest.getProductName());
        product.setQuantity(productUpdateRequest.getQuantity());
        product.setPrice(productUpdateRequest.getPrice());
        product.setProductCreatedDate(product.getProductCreatedDate());
        product.setProductUpdatedDate(new Date());
        Product productResponse = productRepository.save(product);
        log.debug("[{}][updateProduct] -> response : {}", this.getClass().getSimpleName(), productResponse);
        return productResponse;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        return null;
    }
}
