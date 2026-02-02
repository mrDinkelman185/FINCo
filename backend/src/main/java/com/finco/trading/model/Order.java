package com.finco.trading.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", unique = true, nullable = false, length = 50)
    private String orderId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(nullable = false, length = 20)
    private String symbol;

    @Column(name = "order_type", nullable = false, length = 20)
    private String orderType; // MARKET, LIMIT, STOP, STOP_LIMIT

    @Column(nullable = false, length = 10)
    private String side; // BUY, SELL

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(precision = 19, scale = 4)
    private BigDecimal price;

    @Column(nullable = false, length = 20)
    private String status = "PENDING"; // PENDING, FILLED, PARTIALLY_FILLED, CANCELLED, REJECTED

    @Column(name = "filled_quantity", nullable = false, precision = 19, scale = 8)
    private BigDecimal filledQuantity = BigDecimal.ZERO;

    @Column(name = "average_fill_price", precision = 19, scale = 4)
    private BigDecimal averageFillPrice;

    @Column(name = "time_in_force", nullable = false, length = 20)
    private String timeInForce = "DAY"; // DAY, GTC, IOC, FOK

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "executed_at")
    private LocalDateTime executedAt;
}
