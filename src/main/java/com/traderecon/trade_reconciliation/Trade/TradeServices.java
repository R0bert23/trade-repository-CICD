package com.traderecon.trade_reconciliation.Trade;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TradeServices {

    private final TradeRepository tradeRepository;

    public TradeServices(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public Iterable<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeById(Long id) {
        return tradeRepository.findById(id);
    }

    public Trade createTrade(Trade trade) {
        // Example: validation before saving
        if (trade.getPrice() == null || trade.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        return tradeRepository.save(trade);
    }

    public boolean deleteTrade(Long id) {
        Optional<Trade> tradeOptional = tradeRepository.findById(id);
        if (tradeOptional.isPresent()) {
            tradeRepository.delete(tradeOptional.get());
            return true;
        }
        return false;
    }
}
