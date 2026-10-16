package org.example.orderservice.service;

import feign.FeignException;
import org.example.orderservice.client.ProductClient;
import org.example.orderservice.client.ProductResponse;
import org.example.orderservice.dto.CreateOrderRequest;
import org.example.orderservice.entity.Order;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;

    public OrderService(ProductClient productClient,
                        OrderRepository orderRepository) {
        this.productClient = productClient;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(CreateOrderRequest request) {

        // Validate quantity
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        // Call Product Service using OpenFeign
        ProductResponse product;

        try {
            product = productClient.getProduct(request.getProductId());

        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Product not found");

        } catch (FeignException e) {
            throw new RuntimeException("Product Service is unavailable");
        }

        // Check product response
        if (product == null) {
            throw new RuntimeException("Invalid product response");
        }

        // Check stock
        if (product.getStock() == null ||
                product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient product stock");
        }

        // Get current price
        if (product.getPrice() == null) {
            throw new RuntimeException("Invalid product price");
        }

        Double price = product.getPrice();

        // Calculate total
        Double totalAmount = price * request.getQuantity();

        // Create Order entity
        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setPrice(price);
        order.setStatus("NEW");
        order.setTotalAmount(totalAmount);

        // Save order
        return orderRepository.save(order);
    }
}
