package com.traderecon.trade_reconciliation.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
    @Autowired
    private TradeServices tradeServices;

    public TradeController(TradeServices tradeServices) {
        this.tradeServices = tradeServices;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Iterable<Trade>> getAllTrades() {
        return ResponseEntity.ok(this.tradeServices.getAllTrades());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) {
        Trade createdTrade = tradeServices.createTrade(trade);
        return ResponseEntity.status(201).body(createdTrade);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Trade> getTradeById(@PathVariable("id") Long id) {
        Optional<Trade> tradeOpt = this.tradeServices.getTradeById(id);
        return tradeOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTradeById(@PathVariable Long id) {
        boolean deleted = tradeServices.deleteTrade(id);
        if (deleted) {
            return ResponseEntity.ok("Trade deleted successfully"); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
