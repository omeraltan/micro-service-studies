package com.omer.stockmanagement.productservice.controller;

import com.omer.stockmanagement.productservice.enums.Language;
import com.omer.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.omer.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import com.omer.stockmanagement.productservice.repository.entity.Product;
import com.omer.stockmanagement.productservice.request.ProductCreateRequest;
import com.omer.stockmanagement.productservice.request.ProductUpdateRequest;
import com.omer.stockmanagement.productservice.response.FriendlyMessage;
import com.omer.stockmanagement.productservice.response.InternalApiResponse;
import com.omer.stockmanagement.productservice.response.ProductResponse;
import com.omer.stockmanagement.productservice.service.IProductRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/1.0/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductRepositoryService productRepositoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<ProductResponse> createProduct(@PathVariable("language") Language language, @RequestBody ProductCreateRequest productCreateRequest) {

        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        Product product = productRepositoryService.createProduct(language, productCreateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][createProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
            .friendlyMessage(FriendlyMessage.builder()
                .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED))
                .build())
            .httpStatus(HttpStatus.CREATED)
            .hasError(false)
            .payload(productResponse).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{productId}")
    public InternalApiResponse<ProductResponse> getProduct(@PathVariable("language") Language language, @PathVariable("productId") Long productId) {

        log.debug("[{}][getProduct] -> request productId: {}", this.getClass().getSimpleName(), productId);
        Product product = productRepositoryService.getProduct(language, productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][getProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
            .httpStatus(HttpStatus.OK)
            .hasError(false)
            .payload(productResponse)
            .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "{language}/update/{productId}")
    public InternalApiResponse<ProductResponse> updateProduct(@PathVariable("language") Language language, @PathVariable("productId") Long productId, @RequestBody ProductUpdateRequest productUpdateRequest) {
        log.debug("[{}][updateProduct] -> request: {}", this.getClass().getSimpleName(), productUpdateRequest);
        Product product = productRepositoryService.updateProduct(language, productId, productUpdateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][updateProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
            .friendlyMessage(FriendlyMessage.builder()
                .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_UPDATED))
                .build())
            .httpStatus(HttpStatus.OK)
            .hasError(false)
            .payload(productResponse)
            .build();
    }

    @Operation(description = "This endpoint return get all product")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/products")
    public InternalApiResponse<List<ProductResponse>> getProducts(@PathVariable("language") Language language) {
        log.debug("[{}][getProducts]", this.getClass().getSimpleName());
        List<Product> products = productRepositoryService.getAllProducts(language);
        List<ProductResponse> productResponses = convertProductResponseList(products);
        log.debug("[{}][getProducts] -> response: {}", this.getClass().getSimpleName(), productResponses);
            return InternalApiResponse.<List<ProductResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponses)
                .build();

    }

    private List<ProductResponse> convertProductResponseList(List<Product> productList) {
        return productList.stream()
            .map(arg -> ProductResponse.builder()
                .productId(arg.getProductId())
                .productName(arg.getProductName())
                .quantity(arg.getQuantity())
                .price(arg.getPrice())
                .productCreatedDate(arg.getProductCreatedDate())
                .productUpdatedDate(arg.getProductUpdatedDate())
                .build())
            .collect(Collectors.toList());
    }

    private static ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder()
            .productId(product.getProductId())
            .productName(product.getProductName())
            .quantity(product.getQuantity())
            .price(product.getPrice())
            .productCreatedDate(product.getProductCreatedDate())
            .productUpdatedDate(product.getProductUpdatedDate())
            .build();
    }

}
