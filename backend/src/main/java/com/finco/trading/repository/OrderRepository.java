package com.finco.trading.repository;

import com.finco.trading.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderId(String orderId);
    List<Order> findByAccountId(Long accountId);
    List<Order> findBySymbol(String symbol);
    List<Order> findByStatus(String status);
    List<Order> findByAccountIdAndStatus(Long accountId, String status);
}
