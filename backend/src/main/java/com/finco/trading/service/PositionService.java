package com.finco.trading.service;

import com.finco.trading.dto.PositionResponse;
import com.finco.trading.model.Position;
import com.finco.trading.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PositionService {

    private final PositionRepository positionRepository;

    @Cacheable(value = "positions")
    public List<PositionResponse> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "positions", key = "#accountId")
    public List<PositionResponse> getPositionsByAccount(Long accountId) {
        return positionRepository.findByAccountId(accountId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "positions", key = "#symbol")
    public PositionResponse getPositionBySymbol(Long accountId, String symbol) {
        Position position = positionRepository.findByAccountIdAndSymbol(accountId, symbol)
                .orElseThrow(() -> new RuntimeException("Position not found for symbol: " + symbol));
        return toResponse(position);
    }

    private PositionResponse toResponse(Position position) {
        return PositionResponse.builder()
                .id(position.getId())
                .accountId(position.getAccountId())
                .symbol(position.getSymbol())
                .quantity(position.getQuantity())
                .averagePrice(position.getAveragePrice())
                .marketValue(position.getMarketValue())
                .unrealizedPnl(position.getUnrealizedPnl())
                .realizedPnl(position.getRealizedPnl())
                .createdAt(position.getCreatedAt())
                .updatedAt(position.getUpdatedAt())
                .build();
    }
}
