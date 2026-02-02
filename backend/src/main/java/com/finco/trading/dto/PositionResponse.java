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
public class PositionResponse {
    private Long id;
    private Long accountId;
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal averagePrice;
    private BigDecimal marketValue;
    private BigDecimal unrealizedPnl;
    private BigDecimal realizedPnl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
