package com.traderecon.trade_reconciliation.Reconciliation;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ReconciliationRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_date")
    private LocalDateTime runDate;

    private String status;

    @Column(name = "matched_count")
    private Integer matchedCount;

    @Column(name = "unmatched_count")
    private Integer unmatchedCount;

    public ReconciliationRun(Long id, LocalDateTime runDate, String status, Integer matchedCount, Integer unmatchedCount) {
        this.id = id;
        this.runDate = runDate;
        this.status = status;
        this.matchedCount = matchedCount;
        this.unmatchedCount = unmatchedCount;
    }

    public ReconciliationRun() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRunDate() {
        return runDate;
    }

    public void setRunDate(LocalDateTime runDate) {
        this.runDate = runDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMatchedCount() {
        return matchedCount;
    }

    public void setMatchedCount(Integer matchedCount) {
        this.matchedCount = matchedCount;
    }

    public Integer getUnmatchedCount() {
        return unmatchedCount;
    }

    public void setUnmatchedCount(Integer unmatchedCount) {
        this.unmatchedCount = unmatchedCount;
    }
}
