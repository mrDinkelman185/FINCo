package com.finco.trading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long accountId;
    private String symbol;
    private String orderType; // MARKET, LIMIT
    private String side; // BUY, SELL
    private BigDecimal quantity;
    private BigDecimal price; // Optional for MARKET orders
    private String timeInForce; // DAY, GTC, IOC, FOK
}
