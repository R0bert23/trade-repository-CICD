package com.traderecon.trade_reconciliation.Reconciliation;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Data
public class ReconciliationDifference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trade_id")
    private String tradeId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "value_system_a")
    private String valueSystemA;

    @Column(name = "value_system_b")
    private String valueSystemB;

    @ManyToOne
    @JoinColumn(name = "reconciliation_run_id", nullable = false)
    private ReconciliationRun reconciliationRun;

    public ReconciliationDifference(Long id, String tradeId, String fieldName, String valueSystemA, String valueSystemB, ReconciliationRun reconciliationRun) {
        this.id = id;
        this.tradeId = tradeId;
        this.fieldName = fieldName;
        this.valueSystemA = valueSystemA;
        this.valueSystemB = valueSystemB;
        this.reconciliationRun = reconciliationRun;
    }

    public ReconciliationDifference() {
    }

    public Long getId() {
        return id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getValueSystemA() {
        return valueSystemA;
    }

    public String getValueSystemB() {
        return valueSystemB;
    }

    public ReconciliationRun getReconciliationRun() {
        return reconciliationRun;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValueSystemA(String valueSystemA) {
        this.valueSystemA = valueSystemA;
    }

    public void setValueSystemB(String valueSystemB) {
        this.valueSystemB = valueSystemB;
    }

    public void setReconciliationRun(ReconciliationRun reconciliationRun) {
        this.reconciliationRun = reconciliationRun;
    }
}
