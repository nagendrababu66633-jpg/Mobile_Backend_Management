package org.example.orderservice.client;

import org.example.orderservice.exception.ProductServiceUnavailableException;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public ProductResponse getProduct(Long id) {
        throw new ProductServiceUnavailableException(
                "Product service is temporarily unavailable"
        );
    }
}