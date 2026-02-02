package com.finco.trading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderId;
    private Long accountId;
    private String symbol;
    private String orderType;
    private String side;
    private BigDecimal quantity;
    private BigDecimal price;
    private String status;
    private BigDecimal filledQuantity;
    private BigDecimal averageFillPrice;
    private String timeInForce;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime executedAt;
}
