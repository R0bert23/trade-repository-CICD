package com.traderecon.trade_reconciliation.Trade;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity

public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="trade_id",nullable = false)
    private String tradeId;
    private String instrument;
    private Double price;
    private Integer quantity;


    @Column(name="source_system", nullable = false)
    private String sourceSystem;
    @Column(name="trade_date", nullable = false)
    private LocalDate tradeDate;

    public Trade() {
    }

    public Trade(String tradeId, String instrument, Double price, Integer quantity, String sourceSystem, LocalDate tradeDate) {
        this.tradeId = tradeId;
        this.instrument = instrument;
        this.price = price;
        this.quantity = quantity;
        this.sourceSystem = sourceSystem;
        this.tradeDate = tradeDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }


    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

}
