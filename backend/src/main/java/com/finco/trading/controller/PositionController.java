package com.finco.trading.controller;

import com.finco.trading.dto.PositionResponse;
import com.finco.trading.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public ResponseEntity<List<PositionResponse>> getAllPositions(
            @RequestParam(required = false) Long accountId) {
        List<PositionResponse> positions = accountId != null
                ? positionService.getPositionsByAccount(accountId)
                : positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<PositionResponse> getPositionBySymbol(
            @PathVariable String symbol,
            @RequestParam Long accountId) {
        PositionResponse response = positionService.getPositionBySymbol(accountId, symbol);
        return ResponseEntity.ok(response);
    }
}
