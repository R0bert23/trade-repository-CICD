package com.traderecon.trade_reconciliation.Reconciliation;

import com.traderecon.trade_reconciliation.Trade.Trade;
import com.traderecon.trade_reconciliation.Trade.TradeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReconciliationService {

    private final TradeRepository tradeRepository;
    private final ReconciliationRunRepository runRepository;
    private final ReconciliationDifferenceRepository diffRepository;

    public ReconciliationService(TradeRepository tradeRepository, ReconciliationRunRepository runRepository, ReconciliationDifferenceRepository diffRepository) {
        this.tradeRepository = tradeRepository;
        this.runRepository = runRepository;
        this.diffRepository = diffRepository;
    }

    @Transactional
    public ReconciliationRun startReconciliation() {
        List<Trade> allTrades = StreamSupport
                .stream(tradeRepository.findAll().spliterator(), false)
                .toList();

        // Group by tradeId
        Map<String, List<Trade>> groupedTrades = allTrades.stream()
                .collect(Collectors.groupingBy(Trade::getTradeId));

        ReconciliationRun run = new ReconciliationRun();
        run.setRunDate(LocalDateTime.now());
        run.setStatus("IN_PROGRESS");
        run.setMatchedCount(0);
        run.setUnmatchedCount(0);
        run = runRepository.save(run);

        List<ReconciliationDifference> differences = new ArrayList<>();

        for (Map.Entry<String, List<Trade>> entry : groupedTrades.entrySet()) {
            List<Trade> trades = entry.getValue();

            if (trades.size() < 2) continue; // skip if there's no duplicate

            Trade t1 = trades.get(0);
            for (int i = 1; i < trades.size(); i++) {
                Trade t2 = trades.get(i);

                if (!Objects.equals(t1.getPrice(), t2.getPrice())) {
                    differences.add(new ReconciliationDifference(null, t1.getTradeId(), "price",
                            t1.getPrice().toString(), t2.getPrice().toString(), run));
                }

                if (!Objects.equals(t1.getQuantity(), t2.getQuantity())) {
                    differences.add(new ReconciliationDifference(null, t1.getTradeId(), "quantity",
                            t1.getQuantity().toString(), t2.getQuantity().toString(), run));
                }

//                if (!Objects.equals(t1.getInstrument(), t2.getInstrument())) {
//                    differences.add(new ReconciliationDifference(null, t1.getTradeId(), "instrument",
//                            t1.getInstrument(), t2.getInstrument(), run));
//                }
//
//                if (!Objects.equals(t1.getTradeDate(), t2.getTradeDate())) {
//                    differences.add(new ReconciliationDifference(null, t1.getTradeId(), "trade_date",
//                            t1.getTradeDate().toString(), t2.getTradeDate().toString(), run));
//                }

                // Add more fields if needed...
            }
        }

        diffRepository.saveAll(differences);

        run.setStatus("COMPLETED");
        run.setMatchedCount(groupedTrades.size() - differences.size());
        run.setUnmatchedCount(differences.size());
        return runRepository.save(run);
    }

    public List<ReconciliationDifference> getDifferences(Long runId) {
        return diffRepository.findByReconciliationRunId(runId);
    }

    public Optional<ReconciliationRun> getStatus(Long runId) {
        return runRepository.findById(runId);
    }
}
