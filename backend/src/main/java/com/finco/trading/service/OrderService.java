package com.finco.trading.service;

import com.finco.trading.dto.OrderRequest;
import com.finco.trading.dto.OrderResponse;
import com.finco.trading.model.Order;
import com.finco.trading.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ComplianceService complianceService;

    @Transactional
    @CacheEvict(value = "orders", allEntries = true)
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order: {}", request);
        
        // Compliance check
        complianceService.validateOrder(request);
        
        Order order = Order.builder()
                .orderId(generateOrderId())
                .accountId(request.getAccountId())
                .symbol(request.getSymbol())
                .orderType(request.getOrderType())
                .side(request.getSide())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .status("PENDING")
                .filledQuantity(BigDecimal.ZERO)
                .timeInForce(request.getTimeInForce() != null ? request.getTimeInForce() : "DAY")
                .build();
        
        order = orderRepository.save(order);
        log.info("Order created with ID: {}", order.getOrderId());
        
        return toResponse(order);
    }

    @Cacheable(value = "orders", key = "#orderId")
    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        return toResponse(order);
    }

    @Cacheable(value = "orders")
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "orders", key = "#accountId")
    public List<OrderResponse> getOrdersByAccount(Long accountId) {
        return orderRepository.findByAccountId(accountId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "orders", allEntries = true)
    public OrderResponse updateOrder(String orderId, OrderRequest request) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        
        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Cannot update order in status: " + order.getStatus());
        }
        
        if (request.getPrice() != null) {
            order.setPrice(request.getPrice());
        }
        if (request.getQuantity() != null) {
            order.setQuantity(request.getQuantity());
        }
        
        order = orderRepository.save(order);
        return toResponse(order);
    }

    @Transactional
    @CacheEvict(value = "orders", allEntries = true)
    public void cancelOrder(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        
        if ("FILLED".equals(order.getStatus()) || "CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException("Cannot cancel order in status: " + order.getStatus());
        }
        
        order.setStatus("CANCELLED");
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        log.info("Order cancelled: {}", orderId);
    }

    private String generateOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderId(order.getOrderId())
                .accountId(order.getAccountId())
                .symbol(order.getSymbol())
                .orderType(order.getOrderType())
                .side(order.getSide())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .status(order.getStatus())
                .filledQuantity(order.getFilledQuantity())
                .averageFillPrice(order.getAverageFillPrice())
                .timeInForce(order.getTimeInForce())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .executedAt(order.getExecutedAt())
                .build();
    }
}
